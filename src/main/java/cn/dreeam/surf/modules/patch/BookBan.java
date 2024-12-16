package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.MessageUtil;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;

public class BookBan implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Config.preventBookBanEnabled) return;

        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();

        for (ItemStack item : inv.getContents()) {
            // if item is null, skip
            if (item == null || item.getType() == Material.AIR) continue;
            if (!(item.getItemMeta() instanceof BlockStateMeta)) continue;

            BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();

            if (!(meta.getBlockState() instanceof ShulkerBox)) {
                continue;
            }

            ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();

            for (ItemStack shulkerItem : shulkerBox.getInventory().getContents()) {
                if (shulkerItem == null || ItemUtil.isAir(shulkerItem)) continue;
                if (shulkerItem.getType().equals(XMaterial.WRITTEN_BOOK.parseMaterial())) {
                    BookMeta book = (BookMeta) shulkerItem.getItemMeta();
                    if (isBanBook(book)) {
                        player.getWorld().dropItem(player.getLocation(), shulkerItem);
                        shulkerBox.getInventory().remove(shulkerItem);
                    }
                }
            }
            meta.setBlockState(shulkerBox);
            item.setItemMeta(meta);
            MessageUtil.sendMessage(player, Config.preventBookBanMessage);
        }
    }

    // Fix https://github.com/PaperMC/Paper/issues/7866
    @EventHandler
    public void onOpenBook(PlayerInteractEvent event) {
        if (!Config.preventBookBanEnabled) return;

        ItemStack i = event.getItem();

        if (i == null || !ItemUtil.isWritableBook(i) || !i.hasItemMeta()) return;

        BookMeta book = (BookMeta) i.getItemMeta();

        if (isBanBook(book)) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            player.getWorld().dropItem(player.getLocation(), i);
            MessageUtil.sendMessage(player, Config.preventBookBanMessage);
        }
    }

    private boolean isBanBook(BookMeta book) {
        if (book.getPageCount() > 100) {
            return true;
        }

        for (String content : book.getPages()) {
            if (content.contains("nbt")) {
                return true;
            }
        }

        return false;
    }
}
