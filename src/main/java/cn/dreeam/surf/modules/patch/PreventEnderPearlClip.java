package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PreventEnderPearlClip implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        if (!Config.preventTeleportToBlock) return;

        // Only check for ender pearl teleportation
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;

        Location toBlock = event.getTo().getBlock().getLocation();
        Location fromBlock = event.getFrom().getBlock().getLocation();

        // Prevent teleport to same block location to try stuck in the block
        if (toBlock.getX() == fromBlock.getX() && toBlock.getY() == fromBlock.getY() && toBlock.getZ() == fromBlock.getZ()) {
            event.setCancelled(true);

            /*
            Add pearl back to inv
            search for the pearl in the inv, and if player consumes the last pearl in inv,
            create a new one. This can largely prevent pearl name inconsistency.
             */
            Inventory inv = event.getPlayer().getInventory();
            int slot = inv.first(XMaterial.ENDER_PEARL.parseMaterial());
            ItemStack pearl = new ItemStack(XMaterial.ENDER_PEARL.parseMaterial(), 1);
            ItemStack item = slot != -1 ? inv.getItem(slot) : pearl;
            inv.addItem(item != null ? item : pearl);
        }
    }
}
