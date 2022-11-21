package org.surf.modules;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class NetherCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = event.getTo().getWorld();
        // is nether world
        if (world.getEnvironment() != Environment.NETHER) {
            return;
        }
        if (!ConfigCache.NetherEnabled || player.hasPermission("surf.bypass.netherroof")) {
            return;
        }
        // is in nether roof
        if (event.getTo().getBlockY() > ConfigCache.NetherTopLayer) {
            event.setCancelled(true);
            Utils.sendMessage(player, ConfigCache.NetherTopMessage);
            if (ConfigCache.NetherTopBottomDoDamage) {
                player.damage(20);
            }
        // is in nether bottom
        } else if (event.getTo().getBlockY() < ConfigCache.NetherBottomLayer) {
            event.setCancelled(true);
            Utils.sendMessage(player, ConfigCache.NetherBottomMessage);
            if (ConfigCache.NetherTopBottomDoDamage) {
                player.damage(20);
            }
        }
    }
}
