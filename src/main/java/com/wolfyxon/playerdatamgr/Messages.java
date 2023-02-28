package com.wolfyxon.playerdatamgr;

import com.wolfyxon.playerdatamgr.classes.Vector3;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages {
    PlayerDataMgr plugin;
    public Messages(PlayerDataMgr main){plugin = main;}

    public void errorMsg(CommandSender sender,String message){
     sender.sendMessage(ChatColor.RED+message);
    }
    public void clickCommandMsg(CommandSender sender,String message,String command){
        TextComponent msg = new TextComponent(plugin.utils.colored(message));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
        sender.spigot().sendMessage(msg);
    }
    public void clickTpMsg(CommandSender sender, String message, Vector3 pos){
        clickCommandMsg(sender,message,"/teleport "+pos.toString());
    }
    public void clickCopy(CommandSender sender,String message,String copyText){
        TextComponent msg = new TextComponent(plugin.utils.colored(message));
        Text hoverTxt = new Text("Click to copy");
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverTxt);
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copyText));
        sender.spigot().sendMessage(msg);
    }
    public void clickSuggest(CommandSender sender, String message, String command){
        TextComponent msg = new TextComponent(plugin.utils.colored(message));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        sender.spigot().sendMessage(msg);
    }

}
