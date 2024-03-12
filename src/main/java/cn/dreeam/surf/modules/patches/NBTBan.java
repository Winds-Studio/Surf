package cn.dreeam.surf.modules.patches;

import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
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
                if (i.getItemMeta().serialize().size() > ConfigCache.AntiNBTBanLimit) {
                    inv.remove(i);
                    Utils.sendMessage(event.getPlayer(), ConfigCache.AntiNBTBanMessage);
                }
            }
        });
    }
}
