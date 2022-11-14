package org.surf.modules.antilag;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.surf.Main;
import org.surf.util.Utils;

public class BlockPhysics implements Listener {
    private final Main plugin;

    public BlockPhysics(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent event) {
        try {
            int disableTPS = plugin.getConfig().getInt("BlockPhysics-disable-tps");
            if (Utils.getTps() < disableTPS) {
                Material type = event.getBlock().getType();
                if (isChecked(type)) {
                    event.setCancelled(true);
                }
            }
        } catch (Error | Exception throwable) {

        }
    }

    private boolean isChecked(Material material) {
        switch (material) {
            case LAVA:
            case WATER:
                return true;
        }
        return false;
    }
}