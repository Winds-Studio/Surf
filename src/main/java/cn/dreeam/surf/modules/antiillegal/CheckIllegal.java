package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
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
        Inventory playerInv = event.getPlayer().getInventory();

        ItemUtil.deleteIllegals(inv);
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
    @AntiIllegal(EventName = "EntityPickupItemEvent")
    public void onPickup(EntityPickupItemEvent event) {
        if (!Surf.config.antiIllegalCheckWhenItemPickupEnabled()) return;

        ItemStack i = event.getItem().getItemStack();

        if (ItemUtil.isIllegalBlock(i) || ItemUtil.isEnchantedBlock(i)
                || ItemUtil.hasIllegalDurability(i) || ItemUtil.hasIllegalEnchants(i)
                || ItemUtil.hasIllegalItemFlag(i) || ItemUtil.hasIllegalAttributes(i)) {
            event.setCancelled(true);
            event.getItem().remove();
            // Dreeam TODO
        }
    }
}