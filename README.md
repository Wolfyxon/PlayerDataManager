# PlayerData Manager
A simple Minecraft Spigot plugin for managing players' data. Useful in fixing book/shulker bans.
It even works if the player is not currently playing.
### Supported versions
`1.19` `1.19.1` `1.19.2` `1.19.3`  
NOTE: I'm going to downgrade the API version to support higher range of Minecraft versions, not only 1.19+.

## This plugin is still in development
That means it's very limited for now. If you want to test it, you need to compile it yourself.
Using it on a bigger server is not recommended **yet** since it might be unstable.  
JARs will be able to download when plugin exits the very early stage.  
**Contributions appreciated =)**  
Report bugs [here](https://github.com/Wolfyxon/PlayerDataManager/issues).

## Commands & their permissions
All player commands do not require the player to be online.
### /playerdata
Main plugin command.

Usage: `/playerdata [sub-command] [username/UUID] [additional args...] `
<details>
  <summary>Sub-commands</summary>
##### Help
Lists plugin commands and sub-commands of **/playerdata**.

Usage: `/playerdata help`  
Permission: `playerdatamgr.commands.main.help`

##### Get
Gets player's data as JSON.

Usage: `/playerdata get <username or UUID>`  
Permission: `playerdatamgr.commands.main.get`

##### File
Gets player's data **.dat** file inside **playerdata** folder.

Usage: `/playerdata file <username/UUID>`  
Permission: `playerdatamgr.commands.main.file`

##### GetPos
Gets player's last saved position and dimension. 

Usage: `/playerdata getpos <username/UUID>`  
Permission: `playerdatamgr.commands.main.getpos`

##### GetSpawn
Gets player's last saved spawn position and dimension.

Usage: `/playerdata getspawn <username/UUID>`  
Permission: `playerdatamgr.commands.main.getspawn`

##### Reset
Completely resets player's data. Proceed with caution.

Usage: `/playerdata reset <username/UUID>`  
Permission: `playerdatamgr.commands.main.reset`

##### ClearInventory
Clears player's inventory

Usage: `/playerdata clearinventory <username/UUID>`  
Permission: `playerdatamgr.commands.main.clearinventory`

##### ClearEnder
Clears player's enderchest.

Usage: `/playerdata clearender <username/UUID>`  
Permission: `playerdatamgr.commands.main.clearender`

##### Copy
Copies all first player's data to the 2nd player.

Usage: `/playerdata copy <A username/UUID> <B username/UUID>`  
Permission: `playerdatamgr.commands.main.copy`

##### Transfer
Moves all first player's data to the 2nd player.

Usage: `/playerdata transfer <A username/UUID> <B username/UUID>`  
Permission: `playerdatamgr.commands.main.transfer`

</details>

### UUID
Automatically player's gets online UUID from MojangAPI or generates if the server is in offline mode.  
Usage: `/uuid <username>`  
Permission: `playerdatamgr.commands.uuid`

### OnlineUUID
Gets player's UUID from MojangAPI.  
Usage: `/onlineuuid <username>`  
Permission: `playerdatamgr.commands.uuid`

### OfflineUUID
Generates an offline UUID.  
Usage: `/offlineuuid <username>`  
Permission: `playerdatamgr.commands.uuid`

### Seen
Shows the last date a player was online.  
Usage: `/seen <username>`  
Permission: `playerdatamgr.commands.seen`

### FirstPlayed
Shows the date of the first time a player has joined.   
Usage: `/firstjoined <username>`  
Permission: `playerdatamgr.commands.firstjoined`

# Used libraries
- JSON: https://mvnrepository.com/artifact/org.json/json
- NBT: https://github.com/Querz/NBT
