package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SeenCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public SeenCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1){
            plugin.msgs.errorMsg(sender,"No player username or UUID specified.");
            return true;
        }
        
        String usernameOrUUID = args[0];
        Player onlinePlr = Bukkit.getPlayer(usernameOrUUID);
        if(onlinePlr!=null && onlinePlr.isOnline()){
            sender.sendMessage(utils.colored("&a&l"+onlinePlr.getName()+"&r&a is now online!"));
            return true;
        }
        else {
            OfflinePlayer offlinePlr = Bukkit.getOfflinePlayer(usernameOrUUID);
            if(offlinePlr == null){
                plugin.msgs.errorMsg(sender,"Failed to get player");
                return true;
            }
            if(!offlinePlr.hasPlayedBefore()){
                sender.sendMessage(utils.colored("&6&l"+offlinePlr.getName()+"&r&6 hasn't played on the server yet."));
                return true;
            }

            long timestamp = offlinePlr.getLastPlayed();
            long distance = timestamp-new Date().getTime();
            Date lastonline = new Date(timestamp);

            String strDate = utils.dateFormat(lastonline);
            sender.sendMessage(utils.colored("&2&l"+offlinePlr.getName()+"&r&2 was last online on "+strDate));
            return true;
        }
    }
}
