package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.Messages;
import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import java.util.Date;

public class FirstJoinedCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;

    public FirstJoinedCommand(PlayerDataMgr main) {
        plugin = main;
        utils = plugin.utils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            plugin.msgs.sendID(sender, "error.unspecified.player");
            return true;
        }
        String usernameOrUUID = args[0];
        OfflinePlayer offlinePlr = Bukkit.getOfflinePlayer(usernameOrUUID);
        if (offlinePlr == null) {
            plugin.msgs.sendID(sender, "error.playerCantGet");
            return true;
        }
        if (!offlinePlr.hasPlayedBefore()) {
            sender.sendMessage(Messages.formatUsername(plugin.msgs.getMsg("hasntPlayed"), offlinePlr.getName()));
            return true;
        }
        long timestamp = offlinePlr.getFirstPlayed();
        Date firstonline = new Date(timestamp);
        String relative = utils.formatRelativeTime(firstonline);
        String strDate = utils.dateFormat(firstonline);
        sender.sendMessage(
                Messages.formatFullDate(
                        Messages.formatRelativeTime(
                                Messages.formatUsername(
                                        plugin.msgs.getMsg("commands.firstJoined"),
                                        offlinePlr.getName())
                                , relative)
                        , strDate)
        );
        return true;

    }
}
