package org.surf.modules.patches;

import org.bukkit.ChatColor;
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

import java.util.Arrays;

public class ChunkBan implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!ConfigCache.ChunkBanEnabled) {
            return;
        }
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Chunk chunk = block.getChunk();
        if (!player.hasPermission("chunkban.bypass")) {
            if (isChecked(block)) {
                if (chunk.getTileEntities().length > ConfigCache.ChunkBanTileEntityMax) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigCache.ChunkBanPreventMessage));
                }
            }
        }
        if (block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
            // get chunk skull count
            long skullCount = Arrays.stream(chunk.getTileEntities())
                    .filter(tileEntity -> tileEntity.getType() == Material.PLAYER_HEAD || tileEntity.getType() == Material.PLAYER_WALL_HEAD)
                    .count();
            if (skullCount > ConfigCache.ChunkBanSkullMax) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigCache.ChunkBanPreventMessage));
            }
        }
    }

    @EventHandler
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
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigCache.ChunkBanPreventMessage));
            }
        }
    }

    private boolean isChecked(Block block) {
        switch (block.getType()) {
            case FURNACE:
                //TODO
            case TRAPPED_CHEST:
//			case ENCHANTMENT_TABLE:
//			case WALL_BANNER:
            case ACACIA_SIGN:
            case ACACIA_WALL_SIGN:
            case HOPPER:
            case DROPPER:
            case DISPENSER:
            case BREWING_STAND:
            case BEACON:
//			case SIGN_POST:
            case ENDER_CHEST:
            case FLOWER_POT:
            case BLACK_BANNER:
                return true;
            default:
                return false;
        }
    }
}
