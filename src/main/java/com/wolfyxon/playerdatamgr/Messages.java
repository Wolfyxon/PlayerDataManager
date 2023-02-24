package com.wolfyxon.playerdatamgr;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages {
    public void errorMsg(CommandSender sender,String message){
     sender.sendMessage(ChatColor.RED+message);
    }

}
