package cn.dreeam.surf.modules.patches;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BucketEvent implements Listener {

    private static final BlockFace[] POSSIBLE_FACES = {BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST};

    @EventHandler(ignoreCancelled = true)
    public void onBucket(PlayerBucketEmptyEvent event) {
        // Fix monkey code
        if (checkEndPortal(event.getBlockClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (checkEndPortal(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    private boolean checkEndPortal(Block block) {
        if (block.getType() != Material.END_PORTAL_FRAME) {
            return false;
        }

        for (BlockFace face : POSSIBLE_FACES) {
            Block relative = block.getRelative(face);
            if (relative.getType() == Material.END_PORTAL) {
                return true;
            }
        }

        return false;
    }
}