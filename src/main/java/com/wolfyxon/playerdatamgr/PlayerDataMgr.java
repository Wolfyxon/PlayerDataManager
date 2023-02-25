package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.FirstJoinedCommand;
import com.wolfyxon.playerdatamgr.commands.GetDataCommand;
import com.wolfyxon.playerdatamgr.commands.GetUuidCommand;

import com.wolfyxon.playerdatamgr.commands.SeenCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDataMgr extends JavaPlugin {
    public Messages msgs = new Messages();
    public Utils utils = new Utils(this);
    public MojangAPI mojangAPI = new MojangAPI(this);

    @Override
    public void onEnable() {
        getCommand("getuuid").setExecutor(new GetUuidCommand(this));
        getCommand("seen").setExecutor(new SeenCommand(this));
        getCommand("firstjoined").setExecutor(new FirstJoinedCommand(this));
        getCommand("getdata").setExecutor(new GetDataCommand(this));

        Bukkit.getConsoleSender().sendMessage(utils.colored("&aPlayerDataManager has successfully loaded"));

    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands