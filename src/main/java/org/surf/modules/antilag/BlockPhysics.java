package org.surf.modules.antilag;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class BlockPhysics implements Listener {

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent event) {
        if (Utils.getTps() <= ConfigCache.LimitLiquidSpreadDisableTPS) {
            if (isLiquid(event.getBlock().getType())) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isLiquid(Material m) {
        return switch (m) {
            case LAVA, WATER -> true;
            default -> false;
        };
    }
}