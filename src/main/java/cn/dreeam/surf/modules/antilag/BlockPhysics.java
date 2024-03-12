package cn.dreeam.surf.modules.antilag;

import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

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