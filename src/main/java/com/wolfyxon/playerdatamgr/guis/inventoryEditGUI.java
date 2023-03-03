package com.wolfyxon.playerdatamgr.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class inventoryEditGUI implements Listener {
    String name = "Edit inventory";

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        Player plr = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getView().getTitle().equals(name)){
            e.setCancelled(true);
        }

    }
    public void open(Player plr){
        Inventory inv = Bukkit.createInventory(plr,36,name);
        plr.openInventory(inv);
    }

}
