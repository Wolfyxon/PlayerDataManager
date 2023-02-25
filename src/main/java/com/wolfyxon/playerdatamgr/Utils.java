package com.wolfyxon.playerdatamgr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {
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
}
