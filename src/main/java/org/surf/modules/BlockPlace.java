package org.surf.modules;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.surf.Main;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!ConfigCache.IllegalBlockPlaceEnabled) {
            return;
        }
        Player player = event.getPlayer();
        switch (event.getBlock().getType()) {
            case BEDROCK:
            case BARRIER:
            case MOB_SPAWNER:
            case COMMAND_REPEATING:
            case COMMAND_MINECART:
            case COMMAND_CHAIN:
            case COMMAND:
            case ENDER_PORTAL:
            case END_GATEWAY:
            case PORTAL:
            case STRUCTURE_BLOCK:
            case STRUCTURE_VOID:
                event.setCancelled(true);
                Utils.sendMessage(player, ConfigCache.IllegalBlockPlaceMessage);
                event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                break;
            case ENDER_PORTAL_FRAME:
                if (player.getInventory().getItemInMainHand().getType() == Material.ENDER_PORTAL_FRAME || player.getInventory().getItemInOffHand().getType() == Material.ENDER_PORTAL_FRAME) {
                    event.setCancelled(true);
                    Utils.sendMessage(player, ConfigCache.IllegalBlockPlaceMessage);
                    event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                }
                break;
        }
    }
}
