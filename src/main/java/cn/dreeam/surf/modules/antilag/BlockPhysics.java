package cn.dreeam.surf.modules.antilag;

import cn.dreeam.surf.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockPhysics implements Listener {

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent event) {
        if (Util.getTps() <= ConfigCache.LimitLiquidSpreadDisableTPS) {
            if (event.getBlock().isLiquid()) {
                event.setCancelled(true);
            }
        }
    }
}