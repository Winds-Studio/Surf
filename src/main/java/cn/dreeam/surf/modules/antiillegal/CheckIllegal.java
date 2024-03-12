package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.config.ConfigCache;
import cn.dreeam.surf.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CheckIllegal implements Listener {

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "BlockPlaceEvent")
    public void onPlace(BlockPlaceEvent event) {
        if (ConfigCache.AntiillegalBlockPlaceEnabled && ItemUtils.isIllegal(event.getItemInHand())) {
            event.setCancelled(true);

            if (event.getHand() == EquipmentSlot.HAND) {
                event.getPlayer().getInventory().setItemInMainHand(null);
            } else {
                event.getPlayer().getInventory().setItemInOffHand(null);
            }
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "ChunkLoadEvent")
    public void onLoad(ChunkLoadEvent event) {
        if (!ConfigCache.AntiillegalChunkLoadEnabled) {
            return;
        }

        for (BlockState state : event.getChunk().getTileEntities()) {
            if (state instanceof Container) {
                Container container = (Container) state;
                ItemUtils.deleteIllegals(container.getInventory());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "InventoryMoveItemEvent")
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (!ConfigCache.AntiillegalHopperTransferEnabled) {
            return;
        }

        Inventory inv = event.getSource();

        if (inv.getContents().length == 0) {
            return;
        }

        for (ItemStack item : inv.getStorageContents()) {
            if (item != null) {
                if (item.getDurability() > item.getType().getMaxDurability()) {
                    item.setDurability(item.getType().getMaxDurability());
                }

                if (item.getDurability() < 0) {
                    item.setDurability((short) 1);
                }

                if (ItemUtils.isIllegal(item) || ItemUtils.hasIllegalItemFlag(item) || ItemUtils.hasIllegalAttributes(item) || ItemUtils.hasIllegalEnchants(item) || item.hasItemMeta()) {
                    inv.remove(item);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryCloseEvent event) {
        if (ConfigCache.AntiillegalInventoryCloseEnabled) {
            Inventory inv = event.getInventory();
            ItemUtils.deleteIllegals(inv);
            Inventory playerInv = event.getPlayer().getInventory();
            ItemUtils.deleteIllegals(playerInv);
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryOpenEvent")
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inv = event.getInventory();

        if (ConfigCache.AntiillegalInventoryOpenEnabled) {
            ItemUtils.deleteIllegals(inv);
        }
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "PlayerPickupItemEvent")
    public void onPickup(PlayerPickupItemEvent event) {
        if (ConfigCache.AntiillegalItemPickupEnabled) {

            ItemStack item = event.getItem().getItemStack();

            if (ItemUtils.isEnchantedBlock(item) || ItemUtils.hasIllegalItemFlag(item) || ItemUtils.hasIllegalEnchants(item)
                    || ItemUtils.isIllegal(item) || ItemUtils.hasIllegalAttributes(item)) {
                event.setCancelled(true);
                event.getItem().remove();
            }
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerItemHeldEvent")
    public void onItemMove(PlayerItemHeldEvent event) {
        if (ConfigCache.AntiillegalHotbarMoveEnabled) {
            Player player = event.getPlayer();
            ItemUtils.deleteIllegals(player.getInventory());
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerSwapHandItemsEvent")
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        if (ConfigCache.AntiillegalPlayerSwapOffhandEnabled) {
            Player player = event.getPlayer();
            ItemUtils.deleteIllegals(player.getInventory());
        }

        for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
            if (!ConfigCache.AntiillegalDeleteStackedTotem) {
                return;
            }

            if (itemStack != null && itemStack.getType() == Material.TOTEM_OF_UNDYING) {
                if (itemStack.getAmount() > itemStack.getMaxStackSize()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerInteractEvent")
    public void onSwapItem(PlayerInteractEvent event) {
        if (ConfigCache.AntiillegalPlayerInteractEnabled) {
            Player player = event.getPlayer();
            ItemUtils.deleteIllegals(player.getInventory());
        }

        for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
            if (!ConfigCache.AntiillegalDeleteStackedTotem) {
                return;
            }
        }
    }
}