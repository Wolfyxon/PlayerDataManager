package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.TemplateCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataMgr extends JavaPlugin {
    Messages msgs = new Messages();

    @Override
    public void onEnable() {
        getCommand("getuuid").setExecutor(new TemplateCommand(this));
        getLogger().info("PlayerDataManager has started");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands