package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetUuidCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    public GetUuidCommand(PlayerDataMgr main){plugin = main;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        return false;
    }
}
