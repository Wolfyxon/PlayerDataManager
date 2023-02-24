package com.wolfyxon.playerdatamgr;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.profile.PlayerProfile;

import java.util.UUID;

public class Utils {
    /*public String str2uuid(String string){
        return UUID.nameUUIDFromBytes(string.getBytes()).toString();
    }*/

    public UUID getOfflineUuid(String username){
        OfflinePlayer plr = Bukkit.getOfflinePlayer(username);
        return plr.getUniqueId();
    }
}
