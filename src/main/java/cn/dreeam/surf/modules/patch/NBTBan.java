package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class NBTBan implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        if (!Surf.config.preventNBTBanEnabeld()) return;

        Inventory inv = event.getPlayer().getInventory();

        inv.forEach(i -> {
            if (i != null && i.getType().name().contains("SHULKER_BOX")) {
                if (i.getItemMeta().serialize().size() > Surf.config.preventNBTBanLimit()) {
                    inv.remove(i);
                    Util.sendMessage(event.getPlayer(), Surf.config.preventNBTBanMessage());
                }
            }
        });
    }
}
