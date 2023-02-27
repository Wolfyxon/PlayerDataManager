package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.NBTManager;
import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

        //TODO: multiworld support
        if(args.length>0){
            String action = args[0];
            String usernameOrUUID = null;
            UUID uuid = null;
            String filePath = null;
            CompoundTag data = null;
            if(args.length>1 && action != "help"){
                usernameOrUUID = args[1];
                if(utils.strIsUUID(usernameOrUUID)){
                    //Use UUID
                    uuid = UUID.fromString(usernameOrUUID);
                } else {
                    //Use username
                    if(Bukkit.getServer().getOnlineMode()){sender.sendMessage(utils.colored("&7Querying MojangAPI, please wait..."));}
                    uuid = utils.getUUID(usernameOrUUID);
                    if(uuid==null && Bukkit.getServer().getOnlineMode()){plugin.msgs.errorMsg(sender, "Player not found in the Mojang API or no internet connection.");return true;}
                }
                filePath = nbt.playerdataDir+uuid.toString()+".dat";
                sender.sendMessage(filePath);
                if(!utils.file.isPathSafe(filePath,nbt.playerdataDir)){plugin.msgs.errorMsg(sender,"Path traversal detected.");return true;}
                if(!utils.file.fileExists(filePath)){plugin.msgs.errorMsg(sender,"Player data file not found for this user.");return true;}
                data = nbt.tagFromFile(filePath);
                if(data==null){plugin.msgs.errorMsg(sender,"Failed to get playerdata file");return true;}
            }

            switch (action){
                case "help":
                    sender.sendMessage("a");
                    break;
                case "get":
                    JSONObject json = nbt.tag2json(data);
                    sender.sendMessage(utils.prettyJSON(json));
                    break;
                case "reset":
                    break;

                default:
                    plugin.msgs.errorMsg(sender,"Invalid action '"+args[0]+"'. Use /playerdata help for help.");
            }


        }


        return true;
    }

}
