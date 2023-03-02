package com.wolfyxon.playerdatamgr;

import net.querz.nbt.io.*;
import net.querz.nbt.tag.CompoundTag;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;

public class NBTManager {
    PlayerDataMgr plugin;
    Utils utils;
    public String playerdataDir = "world/playerdata/";
    public NBTManager(PlayerDataMgr main){plugin=main;utils=plugin.utils;}

    public static JSONObject fixDoubleArray(JSONObject json,String key){
        JSONArray arr = json.getJSONArray(key);
        json.remove(key);
        for(int i=0; i<arr.length();i++){
            Object v = arr.get(i);
            if(!(v instanceof Double) || ((v instanceof Double) && (((Double)v == Math.floor((Double)v)) && !Double.isInfinite((Double)v)))){
                arr.put(i,Double.parseDouble(String.valueOf(v))+0.0001);
            }
        }
        json.put(key,arr);
        return json;
    }

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

    public static JSONObject snbt2json(String snbt){
        snbt = snbt.replace("I;","");

        return new JSONObject(snbt);
    }

    public static JSONObject tag2json(CompoundTag tag){
        try {
            String raw = SNBTUtil.toSNBT(tag);
            return snbt2json(raw);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getPosition(CompoundTag tag) {
        double result[] = new double[3];
        JSONObject json = tag2json(tag);
        return (JSONArray) json.get("Pos");
    }

}
