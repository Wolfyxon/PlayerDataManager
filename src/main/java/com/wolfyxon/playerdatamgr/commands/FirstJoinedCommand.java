package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            plugin.msgs.sendID(sender,"error.playerUnspecified");
            return true;
        }
        String usernameOrUUID = args[0];
        OfflinePlayer offlinePlr = Bukkit.getOfflinePlayer(usernameOrUUID);
        if (offlinePlr == null) {
            plugin.msgs.errorMsg(sender, "Failed to get player");
            return true;
        }
        if (!offlinePlr.hasPlayedBefore()) {
            sender.sendMessage(utils.colored("&6&l" + offlinePlr.getName() + "&r&6 hasn't played on the server yet."));
            return true;
        }

        long timestamp = offlinePlr.getFirstPlayed();
        long distance = timestamp - new Date().getTime();
        Date firstonline = new Date(timestamp);

        String strDate = utils.dateFormat(firstonline);
        sender.sendMessage(utils.colored("&2&l" + offlinePlr.getName() + "&r&2 played for the first time on " + strDate));
        return true;

    }
}
