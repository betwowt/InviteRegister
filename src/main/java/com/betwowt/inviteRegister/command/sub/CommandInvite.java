package com.betwowt.inviteRegister.command.sub;

import com.betwowt.grpc.InviteRes;
import com.betwowt.inviteRegister.grpc.InviteService;
import com.betwowt.inviteRegister.listener.ListenerBukkitInventory;
import com.betwowt.inviteRegister.utils.EmailUtils;
import io.izzel.taboolib.module.command.base.BaseSubCommand;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.item.Items;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 邀请得业务
 * args  {email,email}
 * 发送给业务端 args {profileId,email}
 */
public class CommandInvite extends BaseSubCommand {

    @TInject(value = "settings.yml",migrate = true)
    private static TConfig tConfig;

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String email = args[0];
        String emailConfirm = args[1];

        if (StringUtils.isBlank(email)){
            TLocale.sendTo(sender,"EMAIL.BLANK");
            return;
        }

        if (!StringUtils.equals(email,emailConfirm)){
            TLocale.sendTo(sender,"EMAIL.CONFIRM");
            return;
        }
        if (!EmailUtils.validate(email)) {
            TLocale.sendTo(sender,"EMAIL.FORMAT-ERROR");
            return;
        }

        String material = tConfig.getString("invite.cost.item");
        int amount = tConfig.getInt("invite.cost.amount");

        String ip = tConfig.getString("grpc.server.ip");
        int port = tConfig.getInt("grpc.server.port");

        InviteService service = new InviteService(ip,port);

        new Thread(()->{
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(material));
                if (!Items.checkItem(player,itemStack,amount,false)) {
                    TLocale.sendTo(sender,"CHECK.MISSING",Items.getName(itemStack),amount);
                    return;
                }
                ListenerBukkitInventory.lock.put(player.getUniqueId(),player);
                InviteRes invite = service.invite(player.getUniqueId().toString().replaceAll("-", ""), email);
                TLocale.sendTo(sender,"EMAIL.INVITE-SUCCESS",invite.getInviteCode());
                if (!Items.checkItem(player,itemStack,amount,true)) {
                    TLocale.sendTo(sender,"CHECK.MISSING",Items.getName(itemStack),amount);
                }
            }catch (Exception e){
                TLocale.sendTo(sender,"EXCEPTION.MESSAGE",e.getMessage());
            }finally {
                service.shutdown();
                ListenerBukkitInventory.lock.remove(player.getUniqueId());
            }
        }).start();
    }


}
