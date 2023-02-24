package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    Main plugin;
    public TestCommand(Main main){
        plugin = main;
        main.getLogger().info("init");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.getLogger().info("EXEC");
        sender.sendMessage("wasup");
        return false;
    }
}
