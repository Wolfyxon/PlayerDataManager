package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
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

        //Command classes
        GetUuidCommand getUUIDclass = new GetUuidCommand(this);
        GetOnlineUuidCommand getOnlineUUIDclass = new GetOnlineUuidCommand(this);
        GetOfflineUuidCommand getOfflineUUIDclass = new GetOfflineUuidCommand(this);
        SeenCommand seenClass = new SeenCommand(this);
        FirstJoinedCommand firstJoinedClass = new FirstJoinedCommand(this);
        PlayerDataCommand playerDataClass = new PlayerDataCommand(this);
        //Commands
        PluginCommand getUUIDcmd = getCommand("getuuid");
        PluginCommand getonlineUUIDcmd = getCommand("getofflineuuid");
        PluginCommand getOfflineUUIDcmd = getCommand("getofflineuuid");
        PluginCommand seenCmd = getCommand("seen");
        PluginCommand firstJoinedCmd = getCommand("firstjoined");
        PluginCommand playerdataCmd = getCommand("playerdata");
        //Executors
        getUUIDcmd.setExecutor(getUUIDclass);
        getonlineUUIDcmd.setExecutor(getOnlineUUIDclass);
        getOfflineUUIDcmd.setExecutor(getOfflineUUIDclass);
        seenCmd.setExecutor(seenClass);
        firstJoinedCmd.setExecutor(firstJoinedClass);
        playerdataCmd.setExecutor(playerDataClass);
        //Tab completers
        playerdataCmd.setTabCompleter(playerDataClass);


        commands = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlayerDataManager")).getDescription().getCommands();
        Bukkit.getConsoleSender().sendMessage(utils.colored("&aPlayerDataManager has successfully loaded"));

    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerDataManager has stopped");
    }
}
//TODO: sender instanceof player for GUI commands