package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.TemplateCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataMgr extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("hi").setExecutor(new TemplateCommand(this));
        getLogger().info("PlayerDataManager has started");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands