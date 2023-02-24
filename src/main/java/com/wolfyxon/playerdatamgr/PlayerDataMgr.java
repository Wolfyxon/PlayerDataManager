package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.GetUuidCommand;

import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataMgr extends JavaPlugin {
    Messages msgs = new Messages();
    Utils utils = new Utils();

    @Override
    public void onEnable() {
        getCommand("getuuid").setExecutor(new GetUuidCommand(this));

        getLogger().info("PlayerDataManager has successfully loaded");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands