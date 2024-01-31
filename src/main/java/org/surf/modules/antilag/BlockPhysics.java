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
        if (Utils.getTps() <= ConfigCache.BlockPhysicsDisableTPS) {
            if (isChecked(event.getBlock().getType())) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isChecked(Material material) {
        return switch (material) {
            case LAVA, WATER -> true;
            default -> false;
        };
    }
}