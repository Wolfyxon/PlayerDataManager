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

import java.awt.*;
import java.util.UUID;

public class GetUuidCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public GetUuidCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //This command will work for both offline and online servers
        if(args.length < 1){
            plugin.msgs.errorMsg(sender,"No player username specified.");
            return true;
        }
        String username = args[0];

        OfflinePlayer plr = Bukkit.getOfflinePlayer(username);
        UUID uuid = plr.getUniqueId();
        if(uuid == null){
            plugin.msgs.errorMsg(sender,"UUID==null. This is a bug, please report!");return true;
        }
        String strUUID = uuid.toString();
        if(strUUID == null){
            plugin.msgs.errorMsg(sender,"strUUID==null. This is a bug, please report!");
            return true;
        }
        sender.sendMessage(utils.colored("&1UUID of &l"+username+"&r&1:"));
        TextComponent msg = new TextComponent(utils.colored("&a"+strUUID));
        Text hoverTxt = new Text("Click to copy");
        msg.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverTxt)
        );
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,strUUID));
        sender.spigot().sendMessage(msg);
        if(!plr.hasPlayedBefore()){
            sender.sendMessage(utils.colored("&6WARNING: This player has never played on this server!"));
        }
        return true;
    }
}
