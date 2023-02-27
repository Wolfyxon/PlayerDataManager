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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    Utils utils;

    public PlayerDataCommand(PlayerDataMgr main) {
        plugin = main;
        utils = plugin.utils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        NBTManager nbt = new NBTManager(plugin);

        if (args.length == 0) {
            sender.sendMessage(utils.colored("&a&lPlayerDataManager"));
            sender.sendMessage(utils.colored("&4by Wolfyxon"));
            sender.sendMessage(utils.colored("&1Source code: &rhttps://github.com/Wolfyxon/PlayerDataManager"));
            sender.sendMessage(utils.colored("&1For list of commands type: &a&l/playerdata help"));
            TextComponent bugMsg = new TextComponent(utils.colored("&4&lReport a bug"));
            bugMsg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Wolfyxon/PlayerDataManager/issues"));
            sender.spigot().sendMessage(bugMsg);
            return true;
        }

        //TODO: multiworld support
        Map<String, String> actions = new HashMap<String, String>();
        actions.put("help","Lists all actions and their usage");
        actions.put("get","Gets raw JSON data from player.");
        actions.put("reset","Completely deletes player's data. Proceed with caution.");

        String action = args[0];
        if(!actions.containsKey(action)){plugin.msgs.errorMsg(sender, "Invalid action '" + args[0] + "'. Use /playerdata help for help.");return true;}
        if(args.length<1){plugin.msgs.errorMsg(sender,"No action specified. See /playerdata help");return true;}
        sender.sendMessage(action);
        if (action.equals("help")) {
            sender.sendMessage(utils.colored("&aUsage: &l/playerdata <action> <username or UUID> <...>"));
            for (Map.Entry entry : actions.entrySet()) {
                String act = (String) entry.getKey();
                String description = (String) entry.getValue();
                sender.sendMessage(utils.colored("&2&l"+act+":&r&6 "+description));
            }
            return true;
        }

        String usernameOrUUID = null;
        UUID uuid = null;
        String filePath = null;
        CompoundTag data = null;
        if (args.length > 1) {
            usernameOrUUID = args[1];
            if (utils.strIsUUID(usernameOrUUID)) {
                uuid = UUID.fromString(usernameOrUUID);
            } else {
                if (Bukkit.getServer().getOnlineMode()) {
                    sender.sendMessage(utils.colored("&7Querying MojangAPI, please wait..."));
                }
                uuid = utils.getUUID(usernameOrUUID);
                if (uuid == null && Bukkit.getServer().getOnlineMode()) {
                    plugin.msgs.errorMsg(sender, "Player not found in the Mojang API or no internet connection.");
                    return true;
                }
            }
            filePath = nbt.playerdataDir + uuid.toString() + ".dat";
            sender.sendMessage(filePath);
            if (!utils.file.isPathSafe(filePath, nbt.playerdataDir)) {
                plugin.msgs.errorMsg(sender, "Path traversal detected.");
                return true;
            }
            if (!utils.file.fileExists(filePath)) {
                plugin.msgs.errorMsg(sender, "Player data file not found for this user.");
                return true;
            }
            data = nbt.tagFromFile(filePath);
            if (data == null) {
                plugin.msgs.errorMsg(sender, "Failed to get playerdata file");
                return true;
            }
        } else {
            plugin.msgs.errorMsg(sender, "Please specify player username or UUID after specified action");
            return true;
        }

        switch (action) {
            case "get":
                JSONObject json = nbt.tag2json(data);
                sender.sendMessage(utils.prettyJSON(json));
                break;
            case "reset":
                break;

        }

        return true;
    }

}
