package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Util;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NetherCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        if (!Surf.config.netherEnabled()) return;

        Player player = event.getPlayer();
        World world = event.getTo().getWorld();

        // check whether nether world and player has bypass perm
        if (world.getEnvironment() != Environment.NETHER || player.hasPermission("surf.bypass.netherroof")) {
            return;
        }

        // is in nether roof
        if (event.getTo().getBlockY() > Surf.config.netherTopLayer()) {
            event.setCancelled(true);
            if (Surf.config.netherTopBottomDoDamage()) {
                player.damage(20);
            }
            Util.sendMessage(player, Surf.config.netherTopMessage());
        // is in nether bottom
        } else if (event.getTo().getBlockY() < Surf.config.netherBottomLayer()) {
            event.setCancelled(true);
            if (Surf.config.netherTopBottomDoDamage()) {
                player.damage(20);
            }
            Util.sendMessage(player, Surf.config.netherBottomMessage());
        }
    }
}
