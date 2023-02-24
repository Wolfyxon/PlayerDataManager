package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        new Main().getLogger().info("AAA");
        sender.sendMessage("wasup");
        return false;
    }
}
