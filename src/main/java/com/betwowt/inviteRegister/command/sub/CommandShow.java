package com.betwowt.inviteRegister.command.sub;

import com.betwowt.grpc.Invite;
import com.betwowt.grpc.ShowRes;
import com.betwowt.inviteRegister.grpc.InviteService;
import io.izzel.taboolib.module.command.base.BaseSubCommand;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 1.查看用户历史邀请
 *
 */
public class CommandShow extends BaseSubCommand {

    @TInject(value = "settings.yml",migrate = true)
    private static TConfig tConfig;

    @Override
    public void onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player  = (Player) commandSender;
        String profileId = player.getUniqueId().toString().replaceAll("-","");

        String ip = tConfig.getString("grpc.server.ip");
        int port = tConfig.getInt("grpc.server.port");

        InviteService service = new InviteService(ip,port);

        new Thread(()->{
            try {
                ShowRes res = service.show(profileId);
                List<Invite> inviteList = res.getInviteList();
                TLocale.sendTo(commandSender, "INVITE.SHOW-HEAD",inviteList.size());
                for (Invite invite : inviteList) {
                    TLocale.sendTo(commandSender, "INVITE.SHOW", invite.getUserEmail(), invite.getStatusDesc());
                }
            }catch (Exception e){
                TLocale.sendTo(commandSender,"EXCEPTION.MESSAGE",e.getMessage());
            }finally {
                service.shutdown();
            }
        }).start();
    }
}
