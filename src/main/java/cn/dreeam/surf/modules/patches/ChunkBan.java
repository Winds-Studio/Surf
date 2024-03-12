package cn.dreeam.surf.modules.patches;

import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
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
        switch (block.getType()) {
            //TODO
            //case ENCHANTMENT_TABLE:
            //case WALL_BANNER:
            //case SIGN_POST:
            case FURNACE:
            case TRAPPED_CHEST:
            case ACACIA_SIGN:
            case ACACIA_WALL_SIGN:
            case HOPPER:
            case DROPPER:
            case DISPENSER:
            case BREWING_STAND:
            case BEACON:
            case ENDER_CHEST:
            case FLOWER_POT:
            case BLACK_BANNER:
            case PLAYER_HEAD:
            case PLAYER_WALL_HEAD:
                return true;
            default:
                return false;
        }
    }

    private boolean isSkull(Material material) {
        switch (material) {
            case PLAYER_HEAD:
            case PLAYER_WALL_HEAD:
                return true;
            default:
                return false;
        }
    }
}
