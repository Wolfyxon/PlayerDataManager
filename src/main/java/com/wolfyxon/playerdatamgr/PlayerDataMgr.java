package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;

public final class PlayerDataMgr extends JavaPlugin {
    public Messages msgs = new Messages(this);
    public Utils utils = new Utils(this);
    public MojangAPI mojangAPI = new MojangAPI(this);
    public Map<String, Map<String,Object>> commands;
    public FileConfiguration config;

    public void initConfig(){
        saveDefaultConfig();
        config = getConfig();
    }

    public void applyConfig(){
        utils.dateFormat = new SimpleDateFormat((String) Objects.requireNonNull(config.get("dateFormat.full")));
    }

    @Override
    public void onEnable() {
        initConfig();
        applyConfig();
        getCommand("getuuid").setExecutor(new GetUuidCommand(this));
        getCommand("getonlineuuid").setExecutor(new GetOnlineUuidCommand(this));
        getCommand("getofflineuuid").setExecutor(new GetOfflineUuidCommand(this));
        getCommand("seen").setExecutor(new SeenCommand(this));
        getCommand("firstjoined").setExecutor(new FirstJoinedCommand(this));
        getCommand("playerdata").setExecutor(new PlayerDataCommand(this));
        commands = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlayerDataManager")).getDescription().getCommands();
        Bukkit.getConsoleSender().sendMessage(utils.colored("&aPlayerDataManager has successfully loaded"));

    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands