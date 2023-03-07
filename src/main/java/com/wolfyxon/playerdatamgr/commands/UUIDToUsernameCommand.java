package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TemplateCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public TemplateCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("wasup");
        return false;
    }
}
