package com.wolfyxon.playerdatamgr;

import java.util.UUID;

public class Utils {
    public String str2uuid(String string){
        return UUID.nameUUIDFromBytes(string.getBytes()).toString();
    }
}
