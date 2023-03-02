package com.wolfyxon.playerdatamgr.classes;

import org.json.JSONArray;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    private double int2double(int integer){
        return Double.parseDouble(String.valueOf(integer));
    }
    private boolean isDoubleInt(double d){
     return ((d == Math.floor(d)) && !Double.isInfinite(d));
    }
    //If one of the axis is a perfect integer JSON will identify it as an int which cannot be put in a double array
    private double formatAxis(double d){
        if(isDoubleInt(d)){return d+0.01;}
        return d;
    }

    public Vector3(double x,double y,double z){
        this.x = formatAxis(x);
        this.y = formatAxis(y);
        this.z = formatAxis(z);
    }
    public Vector3(int x,int y,int z){
        this.x = formatAxis(x);
        this.y = formatAxis(y);
        this.z = formatAxis(z);
    }
    public Vector3(JSONArray jsonArray){
        x = formatAxis((double) jsonArray.get(0));
        y = formatAxis((double) jsonArray.get(1));
        z = formatAxis((double) jsonArray.get(2));
    }

    @Override
    public String toString() {
        return String.valueOf(x)+" "+String.valueOf(y)+" "+String.valueOf(z);
    }
    public String toStringColored(){
        return "&c"+String.valueOf(x)+" &2"+String.valueOf(y)+" &1"+String.valueOf(z);
    }

    public Vector3 intXYZ(){ //do not use with saving
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
