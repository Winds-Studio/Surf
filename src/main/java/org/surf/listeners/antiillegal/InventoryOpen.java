package org.surf.listeners.antiillegal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.surf.Main;

public class InventoryOpen implements Listener {
    Main plugin;

    public InventoryOpen(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryOpenEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.InventoryOpen-Enabled")) {
                Inventory inv = event.getInventory();
                plugin.getItemUtils().deleteIllegals(inv);
            }
        } catch (Error | Exception throwable) {

        }
    }
}