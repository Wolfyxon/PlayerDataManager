package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UUIDToUsernameCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public UUIDToUsernameCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            plugin.msgs.sendID(sender,"error.unspecified.username");
            return true;
        }
        String input = args[0];
        if(!utils.strIsUUID(input)){
            plugin.msgs.sendID(sender,"error.invalid.uuid");
            return true;
        }
        OfflinePlayer plr = Bukkit.getOfflinePlayer(utils.str2uuid(input));
        String username = plr.getName();
        plugin.msgs.clickCopy(sender,"&1Username of &7"+input+"&r:\n"+username,username);

        return true;
    }
}
