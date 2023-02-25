package com.wolfyxon.playerdatamgr;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;

import java.io.File;
import java.io.IOException;

public class NBTManager {
    PlayerDataMgr plugin;
    Utils utils;
    public NBTManager(PlayerDataMgr main){plugin=main;utils=plugin.utils;}

    public CompoundTag parseFile(String path) {
        if(!utils.file.isPathSafe(path)){return null;}
        File f = new File(path);
        try {
            NamedTag tagFile = NBTUtil.read(f);
            CompoundTag root = (CompoundTag) tagFile.getTag();
            return root;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
