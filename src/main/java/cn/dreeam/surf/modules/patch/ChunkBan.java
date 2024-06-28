package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.Util;
import com.cryptomorin.xseries.XMaterial;
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
        if (!Config.perChunkLimitEnabled) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();
        Chunk chunk = block.getChunk();

        if (player.hasPermission("surf.bypass.chunkban")) return;

        if (isTileEntity(block.getType()) && chunk.getTileEntities().length > Config.perChunkLimitTitleEntityMax) {
            event.setCancelled(true);
            Util.sendMessage(player, Config.perChunkLimitMessage);
            return;
        }

        if (isSkull(block.getType())) {
            // get chunk skull count
            long skullCount = Arrays.stream(chunk.getTileEntities()).filter(tileEntity -> isSkull(tileEntity.getType())).count();
            if (skullCount > Config.perChunkLimitSkullMax) {
                event.setCancelled(true);
                Util.sendMessage(player, Config.perChunkLimitMessage);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(PlayerInteractEvent event) {
        if (!Config.perChunkLimitEnabled || event.getClickedBlock() == null || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null) return;

        Player player = event.getPlayer();
        if (player.hasPermission("surf.bypass.chunkban")) return;

        Chunk chunk = event.getClickedBlock().getChunk();
        if (event.getItem().getType().equals(XMaterial.ITEM_FRAME.parseMaterial())) {
            long amount = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof ItemFrame).count();
            if (amount + chunk.getTileEntities().length > Config.perChunkLimitTitleEntityMax) {
                event.setCancelled(true);
                Util.sendMessage(event.getPlayer(), Config.perChunkLimitMessage);
            }
        }
    }

    private boolean isTileEntity(Material m) {
        switch (m) {
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
        return material.equals(XMaterial.PLAYER_HEAD.parseMaterial())
                || material.equals(XMaterial.PLAYER_WALL_HEAD.parseMaterial());
    }
}
