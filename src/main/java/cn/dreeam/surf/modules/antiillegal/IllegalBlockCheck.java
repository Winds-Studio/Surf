package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

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
            case SPAWNER:
            case REPEATING_COMMAND_BLOCK:
            case COMMAND_BLOCK_MINECART:
            case CHAIN_COMMAND_BLOCK:
            case COMMAND_BLOCK:
            case END_PORTAL:
            case END_GATEWAY:
            case NETHER_PORTAL:
            case STRUCTURE_BLOCK:
            case STRUCTURE_VOID:
            case JIGSAW:
            //case LIGHT:
            case END_PORTAL_FRAME:
                event.setCancelled(true);
                Util.sendMessage(player, ConfigCache.IllegalBlockPlaceMessage);
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
