package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class DispenserCrash implements Listener {

    /**
     * Code from:
     * <a href="https://github.com/unionofblackbean/UBBDispenserShulkerBoxCrashFixer/blob/master/src/main/java/asia/ubb/ubbdispensershulkerboxcrashfixer/UBBDispenserShulkerBoxFixerPlugin.java">...</a>
     */
    @EventHandler(ignoreCancelled = true)
    public void dispense(BlockDispenseEvent event) {
        if (!Config.preventDispenserCrash) return;

        Block block = event.getBlock();
        if (block.getType().equals(XMaterial.DISPENSER.parseMaterial())) {
            BlockFace face = ((Directional) block.getBlockData()).getFacing();
            boolean isY0FacingDown = block.getY() == 0 && face == BlockFace.DOWN;
            boolean isYMaxFacingUp = block.getY() == block.getWorld().getMaxHeight() - 1 && face == BlockFace.UP;
            if (isY0FacingDown || isYMaxFacingUp) {
                event.setCancelled(true);

                MessageUtil.println(String.format(
                        "Prevent a dispenser crash at %s.",
                        MessageUtil.locToString(block.getLocation())
                ));
            }
        }
    }
}
