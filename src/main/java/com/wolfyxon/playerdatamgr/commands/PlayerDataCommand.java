package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.NBTManager;
import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import com.wolfyxon.playerdatamgr.Utils;
import com.wolfyxon.playerdatamgr.classes.Vector3;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerDataCommand implements CommandExecutor, TabCompleter {
    PlayerDataMgr plugin;
    Utils utils;

    Map<String, String> actions = new HashMap<String, String>();
    public PlayerDataCommand(PlayerDataMgr main) {
        plugin = main;
        utils = plugin.utils;
        actions.put("help","Lists all actions and their usage");
        actions.put("file","Gets playerdata .dat file path.");
        actions.put("get","Gets raw JSON data from player.");
        actions.put("reset","Completely deletes player's data. Proceed with caution.");
        actions.put("clearinventory","Clears player's inventory. Useful in fixing book/shulker bans.");
        actions.put("clearender","Clears player's enderchest.");
        actions.put("getpos","Gets last player's coordinates.");
        actions.put("getspawn","Gets player's spawn location.");
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

        String action = args[0];
        if(!actions.containsKey(action)){plugin.msgs.errorMsg(sender, "Invalid action '" + args[0] + "'. Use /playerdata help for help.");return true;}
        if(args.length<1){plugin.msgs.errorMsg(sender,"No action specified. See /playerdata help");return true;}
        if (action.equals("help")) {
            sender.sendMessage(utils.colored("&4&lCommands:"));
            for (Map.Entry entry : plugin.commands.entrySet()) {
                Map<String,Object> value = (Map<String, Object>) entry.getValue();
                sender.sendMessage(utils.colored("&2"+entry.getKey()+":&6&o "+value.get("description")));
            }
            sender.sendMessage(utils.colored("&4&lSub-commands"));
            sender.sendMessage(utils.colored("&aUsage: &l/playerdata <action> <username or UUID> <...>"));
            for (Map.Entry entry : actions.entrySet()) {
                String act = (String) entry.getKey();
                String description = (String) entry.getValue();
                sender.sendMessage(utils.colored("&2"+act+":&6&o "+description));
            }
            return true;
        }

        String usernameOrUUID = null;
        UUID uuid = null;
        String filePath = null;
        CompoundTag data = null;
        JSONObject jsonData = null;
        Player plr = null;
        //TODO: fix net.querz.nbt.io.ParseException
        if (args.length > 1) {
            usernameOrUUID = args[1];
            if (utils.strIsUUID(usernameOrUUID)) {
                uuid = UUID.fromString(usernameOrUUID);
            } else {
                if (Bukkit.getServer().getOnlineMode() && !plugin.mojangAPI.isUUIDCachedForUsername(usernameOrUUID)) {
                    plugin.msgs.sendID(sender,"apiConnecting");
                }
                uuid = utils.getUUID(usernameOrUUID);
                if (uuid == null && Bukkit.getServer().getOnlineMode()) {
                    plugin.msgs.errorMsg(sender, "Player not found in the Mojang API or no internet connection.");
                    return true;
                }
            }
            filePath = nbt.playerdataDir + uuid.toString() + ".dat";
            if (!utils.file.isPathSafe(filePath, nbt.playerdataDir)) {
                plugin.msgs.errorMsg(sender, "Path traversal detected.");
                return true;
            }
            if (!utils.file.fileExists(filePath)) {
                plugin.msgs.errorMsg(sender, "Player data file not found for this user.");
                return true;
            }
            plr = Bukkit.getPlayer(uuid);
            if(plr!=null && plr.isOnline()){
                plr.saveData();
            }
            data = nbt.tagFromFile(filePath);
            jsonData = nbt.tag2json(data);
            if (data == null) {
                plugin.msgs.errorMsg(sender, "Failed to get playerdata file");
                return true;
            }
        } else {
            plugin.msgs.sendID(sender,"error.playerUnspecified");
            return true;
        }
        if(plr != null && !plr.isOnline()){plr = null;}

        jsonData = nbt.fixDoubleArray(jsonData,"Pos");
        jsonData = nbt.fixDoubleArray(jsonData,"Rotation");
        jsonData = nbt.fixDoubleArray(jsonData,"Motion");

        switch (action) {
            case "file":
                plugin.msgs.clickCopy(sender,"&1Path: &r"+filePath,filePath);
                break;
            case "get":
                JSONObject json = nbt.tag2json(data);
                sender.sendMessage(utils.prettyJSON(json));
                break;
            case "reset":
                if(args.length > 2 && args[2].equals("confirm")){
                    File f = new File(filePath);
                    if(f.delete()) {
                        sender.sendMessage(utils.colored("&cPlayerdata has been reset for this user."));
                    } else {
                        plugin.msgs.errorMsg(sender,"An error occurred. No data was removed.");
                    }
                } else {
                    sender.sendMessage(utils.colored("&4You are about to COMPLETELY erase this user's data including inventory, "
                            +"achievements, XP and enderchest. Please note that you can use ex. /playerdata clearinventory or clearachievements if "
                            +"you want to remove only a specific part of the data.\nRepeat this command with &lconfirm&r&4 at the end to proceed."));
                }
                break;
            //TODO: make a single function for saving (i wish java had nested functions)


            case "clearinventory":
                jsonData.put("Inventory",new ArrayList<>());
                try {
                    data = (CompoundTag) SNBTUtil.fromSNBT(jsonData.toString());
                    NBTUtil.write(data,new File(filePath));
                    sender.sendMessage(utils.colored("&6Inventory has been cleared."));
                } catch (IOException e) {
                    e.printStackTrace();
                    plugin.msgs.errorMsg(sender,"An error occurred.");
                }
                if(plr!=null){plr.loadData();}
                break;
            case "clearender":
                jsonData.put("EnderItems",new ArrayList<>());
                try {
                    data = (CompoundTag) SNBTUtil.fromSNBT(jsonData.toString());
                    NBTUtil.write(data,new File(filePath));
                    sender.sendMessage(utils.colored("&Enderchest has been cleared."));
                } catch (IOException e) {
                    e.printStackTrace();
                    plugin.msgs.errorMsg(sender,"An error occurred.");
                }
                if(plr!=null){plr.loadData();}
                break;
            case "getpos":
                Vector3 pos = new Vector3((JSONArray) jsonData.get("Pos"));
                sender.sendMessage(utils.colored("Position: "+pos.intXYZ().toStringColored()));
                String dimension = utils.getDimension(jsonData.get("Dimension"));
                sender.sendMessage(utils.colored("Dimension: &2"+dimension));
                plugin.msgs.clickSuggest(sender,"&9&lTeleport","/execute in "+dimension+" run tp @s "+pos.toString());
                break;
            case "getspawn":
                if( !(jsonData.has("SpawnX") && jsonData.has("SpawnY") && jsonData.has("SpawnZ") ) ){
                    plugin.msgs.errorMsg(sender,"This player has no spawn set.");
                    return true;
                }
                int x = jsonData.getInt("SpawnX");int y = jsonData.getInt("SpawnY");int z = jsonData.getInt("SpawnZ");
                Vector3 spawn = new Vector3(x,y,z);
                String spawnDimension = utils.getDimension(jsonData.get("SpawnDimension"));
                sender.sendMessage(utils.colored("Spawn position: "+spawn.toStringColored()));
                plugin.msgs.clickSuggest(sender,"&9&lTeleport","/execute in "+spawnDimension+" run tp @s "+spawn.toString());
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if( !(sender instanceof Player) ){return null;}
        List<String> usage = new ArrayList<>();

        if(args.length == 1) {
            Object[] actionArr = actions.keySet().toArray();
            for (int i = 0; i < actionArr.length; i++) {
                usage.add((String) actionArr[i]);
            }
        }
        if (args.length == 2){
            Object[] players = Bukkit.getOnlinePlayers().toArray();
            for (int i = 0; i < players.length; i++) {
                Player plr = (Player) players[i];
                usage.add(plr.getName());
            }
        }

        return usage;
    }
}
