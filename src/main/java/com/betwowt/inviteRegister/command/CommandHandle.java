package com.betwowt.inviteRegister.command;

import com.betwowt.inviteRegister.command.sub.CommandInvite;
import com.betwowt.inviteRegister.command.sub.CommandShow;
import io.izzel.taboolib.module.command.base.*;

/**
 * 1. 内部邀请命令，手上拿着物品，输入命令后，会发出内部邀请邮件
 */
@BaseCommand(name = "sinvite", aliases ={ "invite"} , permission = "sinvite.access")
public class CommandHandle extends BaseMainCommand {

    @SubCommand(permission = "*", description = "展示所有邀请信息",type = CommandType.PLAYER)
    private BaseSubCommand show = new CommandShow();

    @SubCommand(permission = "*", description = "邀请新用户",aliases = {"i"}, arguments ={"email","email"}, type = CommandType.PLAYER)
    private BaseSubCommand invite = new CommandInvite();
}
