package com.wolfyxon.playerdatamgr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class Utils {
    public String baseDir = System.getProperty("user.dir");
    public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public FileUtils file = new FileUtils();
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

    public boolean strArrContains(String[] array,String string){
        return Arrays.stream(array).anyMatch(string::equals);
    }

    public double[] bytes2doubleArray(byte[] byteArray){
        double[] doubles = new double[byteArray.length / 3];
        for (int i = 0, j = 0; i != doubles.length; ++i, j += 3) {
            doubles[i] = (double)( (byteArray[j  ] & 0xff) |
                    ((byteArray[j+1] & 0xff) <<  8) |
                    ( byteArray[j+2]         << 16));
        }
        return doubles;
    }

    public class FileUtils {
        public boolean isPathSafe(String strPath) {
            File file = new File(baseDir,strPath);
            try {
                return file.getCanonicalPath().startsWith(baseDir);
            } catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }
        public boolean fileExists(String strPath){
            File f = new File(strPath);
            return (f.exists() && !f.isDirectory());
        }
        public boolean dirExists(String strPath){
            File f = new File(strPath);
            return (f.exists() && f.isDirectory());
        }

    }

}
