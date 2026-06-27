package cn.dreeam.surf.modules.misc.patch;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Chunk;
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
        if (!Config.Patch.perChunkLimitEnabled) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();
        Chunk chunk = block.getChunk();

        if (player.hasPermission("surf.bypass.chunkban")) return;

        if (ItemUtil.isTileEntity(block) && chunk.getTileEntities().length > Config.Patch.perChunkLimitTitleEntityMax) {
            event.setCancelled(true);
            MessageUtil.sendMessage(player, Config.Patch.perChunkLimitMessage);
            return;
        }

        if (ItemUtil.isSkull(block.getType())) {
            // get chunk skull count
            long skullCount = Arrays.stream(chunk.getTileEntities()).filter(tileEntity -> ItemUtil.isSkull(tileEntity.getType())).count();
            if (skullCount > Config.Patch.perChunkLimitSkullMax) {
                event.setCancelled(true);
                MessageUtil.sendMessage(player, Config.Patch.perChunkLimitMessage);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(PlayerInteractEvent event) {
        if (!Config.Patch.perChunkLimitEnabled || event.getClickedBlock() == null || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null) return;

        Player player = event.getPlayer();
        if (player.hasPermission("surf.bypass.chunkban")) return;

        Chunk chunk = event.getClickedBlock().getChunk();
        if (event.getItem().getType().equals(XMaterial.ITEM_FRAME.get())) {
            long amount = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof ItemFrame).count();
            if (amount + chunk.getTileEntities().length > Config.Patch.perChunkLimitTitleEntityMax) {
                event.setCancelled(true);
                MessageUtil.sendMessage(event.getPlayer(), Config.Patch.perChunkLimitMessage);
            }
        }
    }
}
