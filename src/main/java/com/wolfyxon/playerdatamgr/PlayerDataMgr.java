package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.GetUuidCommand;

import com.wolfyxon.playerdatamgr.commands.SeenCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataMgr extends JavaPlugin {
    public Messages msgs = new Messages();
    public Utils utils = new Utils();

    @Override
    public void onEnable() {
        getCommand("getuuid").setExecutor(new GetUuidCommand(this));
        getCommand("seen").setExecutor(new SeenCommand(this));

        getLogger().info("PlayerDataManager has successfully loaded");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands