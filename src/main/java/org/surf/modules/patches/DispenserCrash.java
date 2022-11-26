package org.surf.modules.patches;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.material.Directional;

public class DispenserCrash implements Listener {

    /**
     * Code from:
     * https://github.com/unionofblackbean/UBBDispenserShulkerBoxCrashFixer/blob/master/src/main/java/asia/ubb/ubbdispensershulkerboxcrashfixer/UBBDispenserShulkerBoxFixerPlugin.java
     */
    @EventHandler(ignoreCancelled = true)
    public void dispense(BlockDispenseEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.DISPENSER) {
            BlockFace face = ((Directional) block.getState()).getFacing();
            boolean isY0FacingDown = block.getY() == 0 && face == BlockFace.DOWN;
            boolean isYMaxFacingUp = block.getY() == block.getWorld().getMaxHeight() - 1 && face == BlockFace.UP;
            if (isY0FacingDown || isYMaxFacingUp) {
                event.setCancelled(true);
            }
        }
    }
}
