package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import net.querz.nbt.io.*;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;

public class GetDataCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public GetDataCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String world = "world";
        File f = new File(utils.baseDir+"/world/playerdata/8faa349c-3def-4ac3-86a9-d6dbaf3b36af.dat");
        try {
            NamedTag tag = NBTUtil.read(f);
            NamedTag tagFile = NBTUtil.read(f);
            CompoundTag tagRoot = (CompoundTag) tag.getTag();

            plugin.getLogger().info(String.valueOf(tagRoot.getFloat("Health")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
