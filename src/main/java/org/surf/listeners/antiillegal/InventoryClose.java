package org.surf.listeners.antiillegal;

import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
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
                plugin.getItemUtils().deleteIllegals(inv);
                Inventory playerInv = event.getPlayer().getInventory();
                plugin.getItemUtils().deleteIllegals(playerInv);
                if (event.getInventory().getType() == InventoryType.SHULKER_BOX) {
                    Inventory shulkerInv = event.getInventory();
                    for (ItemStack item : shulkerInv.getContents()) {
                        if (item != null) {
                            if (item.getItemMeta() instanceof BlockStateMeta) {
                                BlockStateMeta blockStateMeta = (BlockStateMeta) item.getItemMeta();
                                if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                                    shulkerInv.remove(item);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Error | Exception throwable) {

        }
    }
}