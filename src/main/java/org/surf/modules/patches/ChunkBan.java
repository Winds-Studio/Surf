package org.surf.modules.patches;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

import java.util.Arrays;

public class ChunkBan implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if (!ConfigCache.ChunkBanEnabled) {
            return;
        }
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Chunk chunk = block.getChunk();
        if (player.hasPermission("surf.bypass.chunkban")) {
            return;
        }
        if (isChecked(block) && chunk.getTileEntities().length > ConfigCache.ChunkBanTileEntityMax) {
            event.setCancelled(true);
            Utils.sendMessage(player, ConfigCache.ChunkBanPreventMessage);
            return;
        }
        if (isSkull(block.getType())) {
            // get chunk skull count
            long skullCount = Arrays.stream(chunk.getTileEntities()).filter(tileEntity -> isSkull(tileEntity.getType())).count();
            if (skullCount > ConfigCache.ChunkBanSkullMax) {
                event.setCancelled(true);
                Utils.sendMessage(player, ConfigCache.ChunkBanPreventMessage);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(PlayerInteractEvent event) {
        if (!ConfigCache.ChunkBanEnabled) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null) {
            return;
        }
        Chunk chunk = event.getClickedBlock().getChunk();
        if (event.getItem().getType() == Material.ITEM_FRAME) {
            long amount = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof ItemFrame).count();
            if (amount + chunk.getTileEntities().length > ConfigCache.ChunkBanTileEntityMax) {
                event.setCancelled(true);
                Utils.sendMessage(event.getPlayer(), ConfigCache.ChunkBanPreventMessage);
            }
        }
    }

    private boolean isChecked(Block block) {
        return switch (block.getType()) {
            //TODO
            //case ENCHANTMENT_TABLE:
            //case WALL_BANNER:
            //case SIGN_POST:
            case FURNACE,
                 TRAPPED_CHEST,
                 ACACIA_SIGN,
                 ACACIA_WALL_SIGN,
                 HOPPER,
                 DROPPER,
                 DISPENSER,
                 BREWING_STAND,
                 BEACON,
                 ENDER_CHEST,
                 FLOWER_POT,
                 BLACK_BANNER,
                 PLAYER_HEAD,
                 PLAYER_WALL_HEAD-> true;
            default -> false;
        };
    }

    private boolean isSkull(Material material) {
        return switch (material) {
            case PLAYER_HEAD, PLAYER_WALL_HEAD -> true;
            default -> false;
        };
    }
}
