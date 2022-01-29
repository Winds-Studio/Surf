package org.surf.listeners.antiillegal;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.surf.Main;

public class InventoryClick implements Listener {
    Main plugin;

    public InventoryClick(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryClickEvent")
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.InventoryClick-Enabled")) {
                Inventory inv = event.getInventory();
                plugin.getItemUtils().deleteIllegals(inv);
            }
        } catch (Error | Exception throwable) {

        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        for (ItemStack stack : event.getInventory().getContents()) {
            if (plugin.getConfigBoolean("Antiillegal.Delete-Stacked-Totem")) {
                if (stack != null) {
                    if (stack.getType() == Material.TOTEM_OF_UNDYING) {
                        if (stack.getAmount() > stack.getMaxStackSize()) {
                            stack.setAmount(stack.getMaxStackSize());
                        }
                    }
                }
            }
        }
    }
}