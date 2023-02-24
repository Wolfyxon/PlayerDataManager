package com.wolfyxon.playerdatamgr.commands;

import com.wolfyxon.playerdatamgr.PlayerDataMgr;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class GetUuidCommand implements CommandExecutor {
    PlayerDataMgr plugin;
    public GetUuidCommand(PlayerDataMgr main){plugin = main;}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //This command will work for both offline and online servers
        if(args.length < 1){
            plugin.msgs.errorMsg(sender,"No player username specified.");
            return true;
        }

        UUID uuid = plugin.utils.getOfflineUuid(args[0]);
        if(uuid == null){
            plugin.msgs.errorMsg(sender,"UUID==null. This is a bug, please report!");return true;
        }
        String strUUID = uuid.toString();
        if(strUUID == null){
            plugin.msgs.errorMsg(sender,"strUUID==null. This is a bug, please report!");
            return true;
        }

        sender.sendMessage("UUID: "+uuid);
        return true;
    }
}
