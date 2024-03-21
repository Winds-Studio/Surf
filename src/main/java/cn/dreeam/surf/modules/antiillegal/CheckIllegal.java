package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CheckIllegal implements Listener {


    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "PlayerJoinEvent")
    public void onJoin(PlayerJoinEvent event) {
        if (!Surf.config.antiIllegalCheckWhenPlayerJoinEnabled()) return;

        Inventory inv = event.getPlayer().getInventory();

        ItemUtil.deleteIllegals(inv);
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "InventoryMoveItemEvent")
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (!Surf.config.antiIllegalCheckWhenHopperTransferEnabled()) return;

        Inventory inv = event.getSource();

        if (!inv.getType().equals(InventoryType.CRAFTING)) return;

        ItemUtil.deleteIllegals(inv);
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!Surf.config.antiIllegalCheckWhenInventoryCloseEnabled()) return;

        Inventory inv = event.getPlayer().getInventory();

        if (!inv.getType().equals(InventoryType.PLAYER)) return;

        ItemUtil.deleteIllegals(inv);
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryOpenEvent")
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!Surf.config.antiIllegalCheckWhenInventoryOpenEnabled()) return;

        Inventory inv = event.getPlayer().getInventory();

        if (!inv.getType().equals(InventoryType.PLAYER)) return;

        ItemUtil.deleteIllegals(inv);
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "BlockDispenseArmorEvent")
    public void onDispenseEquip(BlockDispenseArmorEvent event) {
        if (!Surf.config.antiIllegalCheckWhenItemPickupEnabled()) return;

        ItemStack i = event.getItem();

        if (ItemUtil.isIllegalBlock(i) || ItemUtil.isEnchantedBlock(i)
                || ItemUtil.hasIllegalDurability(i) || ItemUtil.isUnbreakable(i) || ItemUtil.hasIllegalEnchants(i)
                || ItemUtil.hasIllegalItemFlag(i) || ItemUtil.hasIllegalAttributes(i)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "EntityPickupItemEvent")
    public void onPickup(EntityPickupItemEvent event) {
        if (!Surf.config.antiIllegalCheckWhenItemPickupEnabled()) return;

        ItemStack i = event.getItem().getItemStack();

        if (ItemUtil.isIllegalBlock(i) || ItemUtil.isEnchantedBlock(i)
                || ItemUtil.hasIllegalDurability(i) || ItemUtil.isUnbreakable(i) || ItemUtil.hasIllegalEnchants(i)
                || ItemUtil.hasIllegalItemFlag(i) || ItemUtil.hasIllegalAttributes(i)) {
            event.setCancelled(true);
            event.getItem().remove();
            if (event.getEntity() instanceof Player) {
                Util.sendMessage(((Player) event.getEntity()), "You can not pick up this illegal item.");
            } else {
                Util.println(event.getEntity().getName() + " Try to pick up an illegal item at " + event.getItem().getLocation());
            }
        }
    }
}