package com.wolfyxon.playerdatamgr;

import net.querz.nbt.io.*;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.DoubleTag;
import net.querz.nbt.tag.ListTag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

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

    public JSONObject snbt2json(String snbt){
        snbt = snbt.replace("I;","");

        return new JSONObject(snbt);
    }

    public JSONObject tag2json(CompoundTag tag){
        /*
        ListTag<?> pos = tag.getListTag("Pos");
        if(pos != null){
            Set<DoubleTag> values = new HashSet<>();
            for(int i=0;i<3;i++){
                values.add((DoubleTag) pos.get(0));
                pos.remove(0);
            }
            DoubleTag[] arrValues = (DoubleTag[]) values.toArray();
            for(int i=0;i<arrValues.length;i++){
                double v = (Double) arrValues[i].asDouble();
                if((v == Math.floor(v)) && !Double.isInfinite(v)){
                    v+=0.01;
                }
                pos.addDouble(v);
            }
        }
        tag.put("Pos",pos);*/

        try {
            String raw = SNBTUtil.toSNBT(tag);
            return snbt2json(raw);

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
