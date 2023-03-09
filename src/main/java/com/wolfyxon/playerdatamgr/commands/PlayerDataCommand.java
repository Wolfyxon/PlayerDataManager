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


    private void save(JSONObject data,String path) {
        try {
            NBTUtil.write((CompoundTag) SNBTUtil.fromSNBT(data.toString()), new File(path));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void reloadPlayer(Player plr){
        if(plr == null) return;
        if(!plr.isOnline()) return;

        plr.loadData();
    }
    private UUID getUUID(String usernameOrUUID,CommandSender sender){
        UUID uuid;
        if (utils.strIsUUID(usernameOrUUID)) {
            uuid = UUID.fromString(usernameOrUUID);
        } else {
            if (Bukkit.getServer().getOnlineMode() && !plugin.mojangAPI.isProfileCached(usernameOrUUID)) {
                plugin.msgs.sendID(sender,"apiConnecting");
            }
            uuid = utils.getUUID(usernameOrUUID);
            if (uuid == null && Bukkit.getServer().getOnlineMode()) {
                plugin.msgs.sendID(sender,"error.api.playerCantGet");
                return null;
            }
        }
        return uuid;
    }
    public String getFilePath(UUID uuid, CommandSender sender){
        String filePath = NBTManager.playerdataDir + uuid.toString() + ".dat";
        if (!utils.file.isPathSafe(filePath, NBTManager.playerdataDir)) {
            plugin.msgs.errorMsg(sender, "Path traversal detected.");
            return null;
        }
        if (!utils.file.fileExists(filePath)) {
            plugin.msgs.errorMsg(sender, "Player data file not found for this user.");
            return null;
        }
        return filePath;
    }
    private JSONObject fixData(JSONObject jsonData){
        jsonData = NBTManager.fixDoubleArray(jsonData,"Pos");
        jsonData = NBTManager.fixDoubleArray(jsonData,"Rotation");
        jsonData = NBTManager.fixDoubleArray(jsonData,"Motion");
        jsonData = NBTManager.fixDoubleArray(jsonData,"Paper.Origin");
        return jsonData;
    }

    Map<String, String> actions = new HashMap<String, String>();
    String[] setActions = {"spawn","pos","gamemode","hp"};
    String[] nonPlayerActions= {"help"};
    String permPrefix = "commands.main.";
    public PlayerDataCommand(PlayerDataMgr main) {
        plugin = main;
        utils = plugin.utils;
        actions.put("help","Lists all actions and their usage");
        actions.put("file","Gets playerdata .dat file path.");
        actions.put("get","Gets raw JSON data from player.");
        actions.put("reset","Completely deletes player's data. Proceed with caution.");
        actions.put("clearinventory","Clears player's inventory. Useful in fixing book/shulker bans.");
        actions.put("clearender","Clears player's enderchest.");
        actions.put("copy","Duplicates player's A data and assigns it to player B.");
        actions.put("transfer","Moves player's A data to player B. Proceed with caution.");
        //actions.put("editinventory","Opens a GUI to edit player's inventory.");
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
        if(!utils.alertPermission(sender, permPrefix+action)){return true;}
        if(args.length<1){plugin.msgs.errorMsg(sender,"No action specified. See /playerdata help");return true;}
        if(Arrays.stream(nonPlayerActions).anyMatch(action::equals)){
            switch (action){
                case "help":
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
                    break;

            }
            return true;
        }

        String usernameOrUUID = null;
        UUID uuid = null;
        String filePath = null;
        CompoundTag data = null;
        JSONObject jsonData = null;
        Player plr = null;

        if (args.length > 1) {
            uuid = getUUID(args[1],sender);
            if(uuid == null) return true;
            filePath = getFilePath(uuid,sender);
            if(filePath == null) return true;
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
            plugin.msgs.sendID(sender,"error.unspecified.player");
            return true;
        }
        if(plr != null && !plr.isOnline()){plr = null;}

        jsonData = fixData(jsonData);

        switch (action) {
            case "file":{
                plugin.msgs.clickCopy(sender,"&2Path: &r\n"+filePath,filePath);
                break;}
            case "get":{
                String strJson = utils.prettyJSON(jsonData);
                sender.sendMessage(strJson);
                plugin.msgs.clickCopy(sender,utils.colored("&2&lClick to copy"),strJson);
                break;}
            case "reset":{
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
                break;}

            case "clearinventory":{
                jsonData.put("Inventory",new ArrayList<>());
                save(jsonData,filePath);
                sender.sendMessage(utils.colored("&6Inventory has been cleared."));
                reloadPlayer(plr);
                break;}
            case "clearender":{
                jsonData.put("EnderItems",new ArrayList<>());
                save(jsonData,filePath);
                sender.sendMessage(utils.colored("&Enderchest has been cleared."));
                reloadPlayer(plr);
                break;}
            case "getpos":{
                Vector3 pos = new Vector3((JSONArray) jsonData.get("Pos"));
                sender.sendMessage(utils.colored("Position: "+pos.intXYZ().toStringColored()));
                String dimension = utils.getDimension(jsonData.get("Dimension"));
                sender.sendMessage(utils.colored("Dimension: &2"+dimension));
                plugin.msgs.clickSuggest(sender,"&9&lTeleport","/execute in "+dimension+" run tp @s "+pos.toString());
                break;}

            case "editinventory":{
                plugin.inventoryEditGUI.open((Player) sender);
                break;}

            case "getspawn":{
                if( !(jsonData.has("SpawnX") && jsonData.has("SpawnY") && jsonData.has("SpawnZ") ) ){
                    plugin.msgs.errorMsg(sender,"This player has no spawn set.");
                    return true;
                }
                int x = jsonData.getInt("SpawnX");int y = jsonData.getInt("SpawnY");int z = jsonData.getInt("SpawnZ");
                Vector3 spawn = new Vector3(x,y,z);
                String spawnDimension = utils.getDimension(jsonData.get("SpawnDimension"));
                sender.sendMessage(utils.colored("Spawn position: "+spawn.toStringColored()));
                plugin.msgs.clickSuggest(sender,"&9&lTeleport","/execute in "+spawnDimension+" run tp @s "+spawn.toString());
                break;}
            case "copy": {
                if(args.length < 3){
                    plugin.msgs.sendID(sender,"error.unspecified.player");
                    sender.sendMessage(utils.colored("&2Usage:"));
                    sender.sendMessage(utils.colored("&a/playerdata copy <from player> <to player>"));
                    sender.sendMessage(utils.colored("&7NOTE: You can use UUIDs instead of usernames."));
                    return true;
                }
                UUID targetUUID = getUUID(args[2],sender);
                if(targetUUID == null) return true;
                String targetPath = getFilePath(targetUUID,sender);
                if(targetPath == null) return true;
                if(nbt.copyData(filePath,targetPath)){
                    sender.sendMessage(utils.colored("&aSuccessfully copied data."));
                } else {
                    plugin.msgs.sendID(sender,"error.general");
                }
                break;}
            case "transfer":{
                if(args.length < 3){
                    plugin.msgs.sendID(sender,"error.unspecified.player");
                    sender.sendMessage(utils.colored("&2Usage:"));
                    sender.sendMessage(utils.colored("&a/playerdata transfer <from player> <to player>"));
                    sender.sendMessage(utils.colored("&7NOTE: You can use UUIDs instead of usernames."));
                    return true;
                }
                if(args.length > 3 && args[3].equals("confirm")) {
                    UUID targetUUID = getUUID(args[2], sender);
                    if (targetUUID == null) return true;
                    String targetPath = getFilePath(targetUUID, sender);
                    if (targetPath == null) return true;
                    if (nbt.copyData(filePath, targetPath)) {
                        if (new File(filePath).delete()) {
                            sender.sendMessage(utils.colored("&aSuccessfully moved data."));
                        } else {
                            plugin.msgs.sendID(sender, "error.general");
                        }
                    } else {
                        plugin.msgs.sendID(sender, "error.general");
                    }
                } else {
                    sender.sendMessage(utils.colored("&4You are about to COMPLETELY erase the first specified player's data including inventory, "
                            +"achievements, XP and enderchest and move it to the 2nd player."
                            +"\nRepeat this command with &lconfirm&r&4 at the end to proceed."));
                }
                break;}
            case "set":{

            break;}
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
                String action = (String) actionArr[i];
                if(utils.hasPermission(sender,permPrefix+action)) {
                    usage.add(action);
                }
            }
            return usage;
        }
        String action = args[0];
        if  ((args.length==3 && (action.equals("copy") || action.equals("transfer"))) || (args.length == 2 && !utils.strArrContains(nonPlayerActions,action) )){
            Object[] players = Bukkit.getOnlinePlayers().toArray();
            for (int i = 0; i < players.length; i++) {
                Player plr = (Player) players[i];
                usage.add(plr.getName());
            }
        }


        return usage;
    }
}
