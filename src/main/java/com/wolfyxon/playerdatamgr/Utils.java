package com.wolfyxon.playerdatamgr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {
    String baseDir = System.getProperty("user.dir");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    PlayerDataMgr plugin;
    public Utils(PlayerDataMgr main){plugin=main;}

    public String dateFormat(Date date){
        return dateFormat.format(date);
    }
    public String colored(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    public UUID getOfflineUuid(String username){
        OfflinePlayer plr = Bukkit.getOfflinePlayer(username);
        return plr.getUniqueId();
    }
    public UUID str2uuid(String string){
        if(string.contains("-")){
            return UUID.fromString(string);
        } else {
            return UUID.fromString( string.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5") );
        }
    }
    public boolean isPathSafe(String strPath) {
        File file = new File(baseDir,strPath);
        try {
            return file.getCanonicalPath().startsWith(baseDir);
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
