package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SimpleBurrow implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!Surf.config.simpleBurrowEnabled()) return;

        if (ItemUtil.isBurrowBlock.contains(event.getBlock().getType().toString())) {
            System.out.println(event.getPlayer().getName());
            System.out.println(event.getPlayer().getLocation().getBlock().getLocation()); // 玩家所处的方块位置，和burrow原地放置的方块的x z相同，也就是和event.getBlock().getLocation()相同
            //System.out.println(event.getBlockPlaced());
            System.out.println(event.getBlock().getLocation());
            System.out.println(event.getItemInHand()); // 一直是对的
            Location playerLoc = event.getPlayer().getLocation();
            if (playerLoc.getBlock().getLocation().equals(event.getBlock().getLocation().clone().add(0, 1, 0))) {
                System.out.println("true");
                event.getPlayer().teleportAsync(new Location(playerLoc.getWorld(), playerLoc.getX(), event.getBlock().getY(), playerLoc.getZ()));
            }
        }
    }
}
