package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("hi").setExecutor(new TestCommand());
        getLogger().info("PlayerDataManager has started");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands