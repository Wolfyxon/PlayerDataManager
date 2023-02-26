package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
public class GetUuidCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public GetUuidCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(Bukkit.getServer().getOnlineMode()){
            new GetOnlineUuidCommand(plugin).onCommand(sender,cmd,label,args);
        } else {
            new GetOfflineUuidCommand(plugin).onCommand(sender,cmd,label,args);
        }
        return true;
    }
}
