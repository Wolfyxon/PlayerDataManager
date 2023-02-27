package com.wolfyxon.playerdatamgr.classes;

public class Vector3 {
    public double x;
    public double y;
    public double z;
    public Vector3(double x,double y,double z){
        this.x = x;
        this.y = x;
        this.z = x;
    }
    public static Vector3 fromArray(double[] doubleArray){
        return new Vector3(doubleArray[0],doubleArray[1],doubleArray[2]);
    }

}
