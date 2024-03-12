package cn.dreeam.surf.modules.patches;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;

import java.util.regex.Pattern;

public class BookBan implements Listener {

    private final Pattern PATTERN = Pattern.compile("[^a-zA-Z0-9]");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
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
                if (shulkerItem == null || shulkerItem.getType() == Material.AIR) continue;
                if (shulkerItem.getType() == Material.WRITTEN_BOOK) {
                    BookMeta book = (BookMeta) shulkerItem.getItemMeta();
                    if (isBanBook(book)) {
                        player.getWorld().dropItem(player.getLocation(), shulkerItem);
                        shulkerBox.getInventory().remove(shulkerItem);
                    }
                }
            }
            meta.setBlockState(shulkerBox);
            item.setItemMeta(meta);
        }
    }

    private boolean isBanBook(BookMeta book) {
        for (String bookPages : book.getPages()) {
            if (PATTERN.matcher(bookPages).find()) {
                return true;
            }
        }

        return false;
    }
}
