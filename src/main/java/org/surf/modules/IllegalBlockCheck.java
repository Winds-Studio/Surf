package org.surf.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class IllegalBlockCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!ConfigCache.IllegalBlockPlaceEnabled) {
            return;
        }
        Player player = event.getPlayer();
        // GET HAND
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
            case ENDER_PORTAL_FRAME:
                event.setCancelled(true);
                Utils.sendMessage(player, ConfigCache.IllegalBlockPlaceMessage);
                // clear item by hand
                if (event.getHand() == EquipmentSlot.HAND) {
                    player.getInventory().setItemInMainHand(null);
                } else {
                    player.getInventory().setItemInOffHand(null);
                }
                break;
            // clear item by hand
        }
    }
}
