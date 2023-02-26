package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.NBTManager;
import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONArray;

import java.io.File;

public class PlayerDataCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;
    public PlayerDataCommand(PlayerDataMgr main){plugin = main;utils = plugin.utils;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        NBTManager nbt = new NBTManager(plugin);

        if(args.length==0){
            sender.sendMessage(utils.colored("&a&lPlayerDataManager"));
            sender.sendMessage(utils.colored("&4by Wolfyxon"));
            sender.sendMessage(utils.colored("&1Source code: &rhttps://github.com/Wolfyxon/PlayerDataManager"));
            sender.sendMessage(utils.colored("&1For list of commands type: &a&l/playerdata help"));
            TextComponent bugMsg = new TextComponent(utils.colored("&4&lReport a bug"));
            bugMsg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://github.com/Wolfyxon/PlayerDataManager/issues"));
            sender.spigot().sendMessage(bugMsg);
            return true;
        }
        if(args.length>0){
            String action = args[0];
            switch (action){
                case "help":
                    sender.sendMessage("a");
                    break;


                default:
                    plugin.msgs.errorMsg(sender,"Invalid action '"+args[0]+"'. Use /playerdata help for help.");
            }


        }


        return true;
    }

}
