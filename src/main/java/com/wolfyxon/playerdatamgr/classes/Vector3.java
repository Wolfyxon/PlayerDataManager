package com.wolfyxon.playerdatamgr.classes;

import org.json.JSONArray;

public class Vector3 {
    public double x;
    public double y;
    public double z;
    public Vector3(double x,double y,double z){
        this.x = x;
        this.y = x;
        this.z = x;
    }

    @Override
    public String toString() {
        return String.valueOf(x)+" "+String.valueOf(y)+" "+String.valueOf(z);
    }
    public String toStringColored(){
        return "&c"+String.valueOf(x)+" &2"+String.valueOf(y)+" &1"+String.valueOf(z);
    }

    public static Vector3 fromArray(JSONArray doubleArray){
        return new Vector3((Double) doubleArray.get(0),(Double) doubleArray.get(1),(Double) doubleArray.get(2));
    }

}
