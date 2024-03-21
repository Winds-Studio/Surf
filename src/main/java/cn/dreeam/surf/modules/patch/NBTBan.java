package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Util;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.atomic.AtomicInteger;

public class NBTBan implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        if (!Surf.config.preventNBTBanEnabeld()) return;

        Inventory inv = event.getPlayer().getInventory();
        AtomicInteger itemSize = new AtomicInteger();

        inv.forEach(i -> {
            if (i != null && i.getType().name().contains("SHULKER_BOX")) {
                itemSize.addAndGet(NBT.itemStackToNBT(i).toString().length());

                if (itemSize.get() > Surf.config.preventNBTBanLimit()) {
                    inv.remove(i);
                    Util.sendMessage(event.getPlayer(), Surf.config.preventNBTBanMessage());
                }
            }
        });
    }
}
