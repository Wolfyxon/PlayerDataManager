# PlayerData Manager
A simple Minecraft Spigot plugin for managing players' data. Useful in fixing book/shulker bans.
It even works if the player is not currently playing.
## Info
### Supported versions
`1.19` `1.19.1` `1.19.2` `1.19.3`

## This plugin is still in development
That means it's very limited for now. If you want to test it, you need to compile it yourself.
Using it on a bigger server is not recommended **yet** since it might be unstable.  
JARs will be able to download when plugin exits the very early stage.  
**Contributions appreciated =)**  
Report bugs [here](https://github.com/Wolfyxon/PlayerDataManager/issues).

## Commands & their permissions
### /playerdata
Main plugin command.

Usage: `/playerdata [sub-command] [username/UUID] [additional args...] `
#### Sub-commands
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

Usage: `/playerdata file <username or UUID>`  
Permission: `playerdatamgr.commands.main.file`

##### GetPos
Gets player's last saved position and dimension. 

Usage: `/playerdata getpos <username or UUID>`  
Permission: `playerdatamgr.commands.main.getpos`

##### GetSpawn
Gets player's last saved spawn position and dimension.

Usage: `/playerdata getspawn <username or UUID>`  
Permission: `playerdatamgr.commands.main.getspawn`

##### Reset
Completely resets player's data. Proceed with caution.

Usage: `/playerdata reset <username or UUID>`  
Permission: `playerdatamgr.commands.main.reset`

##### ClearInventory
Clears player's inventory

Usage: `/playerdata clearinventory <username or UUID>`  
Permission: `playerdatamgr.commands.main.clearinventory`

##### ClearEnder
Clears player's enderchest.

Usage: `/playerdata clearender <username or UUID>`  
Permission: `playerdatamgr.commands.main.clearender`
