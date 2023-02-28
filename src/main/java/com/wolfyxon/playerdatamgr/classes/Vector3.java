package com.wolfyxon.playerdatamgr.classes;

import org.json.JSONArray;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    private double int2double(int integer){
        return Double.parseDouble(String.valueOf(integer));
    }

    public Vector3(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(JSONArray jsonArray){
        x = (double) jsonArray.get(0);
        y = (double) jsonArray.get(1);
        z = (double) jsonArray.get(2);
    }

    @Override
    public String toString() {
        return String.valueOf(x)+" "+String.valueOf(y)+" "+String.valueOf(z);
    }
    public String toStringColored(){
        return "&c"+String.valueOf(x)+" &2"+String.valueOf(y)+" &1"+String.valueOf(z);
    }

    public Vector3 intXYZ(){
        int intX = (int) x;
        int intY = (int) y;
        int intZ = (int) z;
        return new Vector3(
                int2double(intX),
                int2double(intY),
                int2double(intZ)
        );
    }


}
