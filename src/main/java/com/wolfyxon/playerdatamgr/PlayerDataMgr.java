package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataMgr extends JavaPlugin {
    public Messages msgs = new Messages(this);
    public Utils utils = new Utils(this);
    public MojangAPI mojangAPI = new MojangAPI(this);

    @Override
    public void onEnable() {
        getCommand("getuuid").setExecutor(new GetUuidCommand(this));
        getCommand("getonlineuuid").setExecutor(new GetOnlineUuidCommand(this));
        getCommand("getofflineuuid").setExecutor(new GetOfflineUuidCommand(this));
        getCommand("seen").setExecutor(new SeenCommand(this));
        getCommand("firstjoined").setExecutor(new FirstJoinedCommand(this));
        getCommand("playerdata").setExecutor(new PlayerDataCommand(this));

        Bukkit.getConsoleSender().sendMessage(utils.colored("&aPlayerDataManager has successfully loaded"));


    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands