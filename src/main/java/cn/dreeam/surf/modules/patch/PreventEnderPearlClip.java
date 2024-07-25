package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

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
        }
    }
}
