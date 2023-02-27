package com.wolfyxon.playerdatamgr;

import net.querz.nbt.io.*;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class NBTManager {
    PlayerDataMgr plugin;
    Utils utils;
    public String playerdataDir = "world/playerdata/";
    public NBTManager(PlayerDataMgr main){plugin=main;utils=plugin.utils;}

    public CompoundTag tagFromFile(String path) {
        if(!utils.file.isPathSafe(path,utils.baseDir)){return null;}
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
    public JSONObject tag2json(CompoundTag tag){
        try {
            String raw = SNBTUtil.toSNBT(tag).replace("I;",""); //possibly a library bug
            return new JSONObject(raw);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getPosition(CompoundTag tag) {
        double result[] = new double[3];
        JSONObject json = tag2json(tag);
        return (JSONArray) json.get("Pos");
    }

}
