package org.surf.listeners.antiillegal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.surf.Main;

public class InventoryClose implements Listener {
    Main plugin;

    public InventoryClose(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryCloseEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.InventoryClose-Enabled")) {
                Inventory inv = event.getInventory();
                plugin.getItemUtils().deleteIllegals((PlayerInventory) inv);
                Inventory playerInv = event.getPlayer().getInventory();
                plugin.getItemUtils().deleteIllegals((PlayerInventory) playerInv);
            }
        } catch (Error | Exception throwable) {

        }
    }
}