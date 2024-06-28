package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.Util;
import com.cryptomorin.xseries.XMaterial;
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
        if (!Config.preventBuketPortalEnabled) return;

        // Fix monkey code
        if (checkEndPortal(event.getBlockClicked())) {
            event.setCancelled(true);
            Util.sendMessage(event.getPlayer(), Config.preventBuketPortalMessage);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (!Config.preventBuketPortalEnabled) return;

        if (checkEndPortal(event.getBlock())) {
            event.setCancelled(true);
            Util.println(Config.preventBuketPortalMessage + " | " + event.getBlock().getLocation());
        }
    }

    private boolean checkEndPortal(Block block) {
        if (!block.getType().equals(XMaterial.END_PORTAL_FRAME.parseMaterial())) {
            return false;
        }

        for (BlockFace face : POSSIBLE_FACES) {
            Block relative = block.getRelative(face);
            if (relative.getType().equals(XMaterial.END_PORTAL.parseMaterial())) {
                return true;
            }
        }

        return false;
    }
}