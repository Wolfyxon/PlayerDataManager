package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.NBTManager;
import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONArray;

import java.io.File;

public class PlayerDataCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public PlayerDataCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        NBTManager nbt = new NBTManager(plugin);

        String world = "world";
        File f = new File(utils.baseDir+"/world/playerdata/8faa349c-3def-4ac3-86a9-d6dbaf3b36af.dat");
        CompoundTag tag = nbt.tagFromFile(utils.baseDir+"/world/playerdata/8faa349c-3def-4ac3-86a9-d6dbaf3b36af.dat");
        JSONArray pos = nbt.getPosition(tag);
        for(int i=0;i<pos.length();i++){
            plugin.getLogger().info(String.valueOf(pos.get(i)));
        }

        return true;
    }

}
