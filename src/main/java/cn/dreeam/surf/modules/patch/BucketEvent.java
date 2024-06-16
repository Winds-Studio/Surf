package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.Surf;
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
        if (!Surf.config.preventBuketPortalEnabled()) return;

        // Fix monkey code
        if (checkEndPortal(event.getBlockClicked())) {
            event.setCancelled(true);
            Util.sendMessage(event.getPlayer(), Surf.config.preventBuketPortalMessage());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (!Surf.config.preventBuketPortalEnabled()) return;

        if (checkEndPortal(event.getBlock())) {
            event.setCancelled(true);
            Util.println(Surf.config.preventBuketPortalMessage() + " | " + event.getBlock().getLocation());
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