package com.wolfyxon.playerdatamgr;

import net.querz.nbt.tag.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public String baseDir = System.getProperty("user.dir");
    public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public FileUtils file = new FileUtils();
    PlayerDataMgr plugin;
    public MojangAPI mojangAPI;
    public Map<Integer, String> dimensions = new HashMap<Integer, String>();
    public Utils(PlayerDataMgr main){
        plugin=main;mojangAPI = plugin.mojangAPI;
        dimensions.put(-1,"minecraft:nether");
        dimensions.put(-1,"minecraft:overworld");
        dimensions.put(1,"minecraft:the_end");
    }
    String permissionPrefix = "playerdatamgr.";
    public boolean hasPermission(CommandSender sender,String permission){
        return sender.hasPermission(permissionPrefix+permission);
    }
    public boolean alertPermission(CommandSender sender,String permission){
        if(sender instanceof ConsoleCommandSender){return true;}
        if(sender.isOp()){return true;}
        boolean has = hasPermission(sender,permission);
        if (!has){plugin.msgs.sendID(sender,"error.noPermission");}
        return has;
    }

    public String formatRelativeTime(Date date){
        return new PrettyTime(new Locale((String) plugin.config.get("dateFormat.relativeLocale"))).format(date);
    }

    public String dateFormat(Date date){
        return dateFormat.format(date);
    }
    public String colored(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    public UUID getOfflineUUID(String username){
        OfflinePlayer plr = Bukkit.getOfflinePlayer(username);
        return plr.getUniqueId();
    }
    public UUID getOnlineUUID(String username){
        return plugin.mojangAPI.getOnlineUUID(username);
    }
    public UUID getUUID(String username){
        if(Bukkit.getServer().getOnlineMode()){
            return getOnlineUUID(username);
        } else {
            return getOfflineUUID(username);
        }
    }
    public UUID str2uuid(String string){
        if(string.contains("-")){
            return UUID.fromString(string);
        } else {
            return UUID.fromString( string.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5") );
        }
    }

    public boolean strIsUUID(String string){
        try{
            UUID uuid = UUID.fromString(string);
            return true;
        } catch (IllegalArgumentException exception){
            return false;
        }
    }

    public static boolean strArrContains(String[] array,String string){
        return Arrays.stream(array).anyMatch(string::equals);
    }

    public String prettyJSON(JSONObject json){
        return json.toString(4);
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

    public String dimensionFromID(int id){
        return dimensions.get(id);
    }

    public String getDimension(Object idOrName){
        if(idOrName instanceof String){
            if(idOrName.equals("minecraft:nether")){return "minecraft:the_nether";} //It's different for playerdata and commands
            if(dimensions.containsKey(idOrName)){
                return dimensions.get(idOrName);
            } else {
                return "minecraft:overworld";
            }
        }
        if(idOrName instanceof Integer){
            return dimensionFromID( (int) idOrName);
        }
        return null;
    }

    public static class ItemUtils {
        public static ItemStack tag2item(CompoundTag tag){
            //TODO: this
            JSONObject json = NBTManager.tag2json(tag);
            Material m = Material.getMaterial(json.getString("id"));
            ItemStack stack = Bukkit.getItemFactory().createItemStack(m.name());
            stack.setAmount(json.getInt("Count"));


            return stack;
        }
    }

    public class FileUtils {
        public boolean isPathSafe(String strPath,String base) {
            File file = new File(baseDir+base,strPath);
            try {
                return file.getCanonicalPath().startsWith(baseDir+base);
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
