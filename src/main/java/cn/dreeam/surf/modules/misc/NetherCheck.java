package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NetherCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        if (!Config.netherEnabled) return;

        Player player = event.getPlayer();
        World world = event.getTo().getWorld();

        // check whether nether world and player has bypass perm
        if (world.getEnvironment() != Environment.NETHER || player.hasPermission("surf.bypass.netherroof")) {
            return;
        }

        // is in nether roof
        if (event.getTo().getBlockY() > Config.netherTopLayer) {
            event.setCancelled(true);
            if (Config.netherTopBottomDoDamage) {
                player.damage(20);
            }
            MessageUtil.sendMessage(player, Config.netherTopMessage);
        // is in nether bottom
        } else if (event.getTo().getBlockY() < Config.netherBottomLayer) {
            event.setCancelled(true);
            if (Config.netherTopBottomDoDamage) {
                player.damage(20);
            }
            MessageUtil.sendMessage(player, Config.netherBottomMessage);
        }
    }
}
