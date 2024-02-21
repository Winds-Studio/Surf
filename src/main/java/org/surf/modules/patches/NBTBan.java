package org.surf.modules.patches;

import org.surf.util.ConfigCache;
import org.surf.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class NBTBan implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Inventory inv = event.getPlayer().getInventory();

        inv.forEach(i -> {
            if (i != null && i.getType().name().contains("SHULKER_BOX")) {
                if (i.getItemMeta().getAsString().length() > ConfigCache.AntiNBTBanLimit) {
                    inv.remove(i);
                    Utils.sendMessage(event.getPlayer(), ConfigCache.AntiNBTBanMessage);
                }
            }
        });
    }
}
