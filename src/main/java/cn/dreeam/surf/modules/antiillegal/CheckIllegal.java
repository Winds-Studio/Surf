package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CheckIllegal implements Listener {

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "InventoryMoveItemEvent")
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (!Surf.config.antiIllegalCheckWhenHopperTransferEnabled()) return;

        Inventory inv = event.getSource();

        if (inv.getContents().length == 0) return;

        for (ItemStack item : inv.getStorageContents()) {
            if (item != null) {
                if (item.getDurability() > item.getType().getMaxDurability()) {
                    item.setDurability(item.getType().getMaxDurability());
                }

                if (item.getDurability() < 0) {
                    item.setDurability((short) 1);
                }

                if (ItemUtil.isIllegalBlock(item) || ItemUtil.hasIllegalItemFlag(item) || ItemUtil.hasIllegalAttributes(item) || ItemUtil.hasIllegalEnchants(item) || item.hasItemMeta()) {
                    inv.remove(item);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!Surf.config.antiIllegalCheckWhenInventoryCloseEnabled()) return;

        Inventory inv = event.getInventory();
        ItemUtil.deleteIllegals(inv);
        Inventory playerInv = event.getPlayer().getInventory();
        ItemUtil.deleteIllegals(playerInv);
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryOpenEvent")
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!Surf.config.antiIllegalCheckWhenInventoryOpenEnabled()) return;

        Inventory inv = event.getInventory();
        ItemUtil.deleteIllegals(inv);
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "PlayerPickupItemEvent")
    public void onPickup(PlayerPickupItemEvent event) {
        if (!Surf.config.antiIllegalCheckWhenItemPickupEnabled()) return;

        ItemStack item = event.getItem().getItemStack();

        if (ItemUtil.isEnchantedBlock(item) || ItemUtil.hasIllegalItemFlag(item) || ItemUtil.hasIllegalEnchants(item)
                || ItemUtil.isIllegalBlock(item) || ItemUtil.hasIllegalAttributes(item)) {
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerItemHeldEvent")
    public void onItemMove(PlayerItemHeldEvent event) {
        if (!Surf.config.antiIllegalCheckWhenHotbarMoveEnabled()) return;

        Player player = event.getPlayer();
        ItemUtil.deleteIllegals(player.getInventory());
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerSwapHandItemsEvent")
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        if (!Surf.config.antiIllegalCheckWhenPlayerSwapOffhandEnabled()) return;

        Player player = event.getPlayer();
        ItemUtil.deleteIllegals(player.getInventory());
    }

    @EventHandler
    @AntiIllegal(EventName = "PlayerInteractEvent")
    public void onSwapItem(PlayerInteractEvent event) {
        if (!Surf.config.antiIllegalCheckWhenPlayerInteractEnabled()) return;

        Player player = event.getPlayer();
        ItemUtil.deleteIllegals(player.getInventory());
    }
}