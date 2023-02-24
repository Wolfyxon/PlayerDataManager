package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.TestCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginCommand hiCommand = getCommand("hi");
        CommandExecutor hiClass = new TestCommand(this);
        getLogger().info(hiCommand.toString());
        getLogger().info(hiClass.toString());
        hiCommand.setExecutor(hiClass);
        getLogger().info("PlayerDataManager has started");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands