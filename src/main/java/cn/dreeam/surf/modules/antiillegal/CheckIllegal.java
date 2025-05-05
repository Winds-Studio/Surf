package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.MessageUtil;
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
    private void onJoin(PlayerJoinEvent event) {
        if (!Config.antiIllegalCheckWhenPlayerJoinEnabled) return;

        Inventory inv = event.getPlayer().getInventory();

        ItemUtil.cleanIllegals(inv, event.getPlayer().getName());
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "InventoryMoveItemEvent")
    private void onInventoryMove(InventoryMoveItemEvent event) {
        if (!Config.antiIllegalCheckWhenHopperTransferEnabled) return;

        Inventory inv = event.getSource();

        if (!inv.getType().equals(InventoryType.CRAFTING)) return;

        ItemUtil.cleanIllegals(inv, inv.getType().name());
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryCloseEvent")
    private void onInventoryClose(InventoryCloseEvent event) {
        if (!Config.antiIllegalCheckWhenInventoryCloseEnabled) return;

        Inventory inv = event.getPlayer().getInventory();

        if (!inv.getType().equals(InventoryType.PLAYER)) return;

        ItemUtil.cleanIllegals(inv, event.getPlayer().getName());
    }

    @EventHandler
    @AntiIllegal(EventName = "InventoryOpenEvent")
    private void onInventoryOpen(InventoryOpenEvent event) {
        if (!Config.antiIllegalCheckWhenInventoryOpenEnabled) return;

        Inventory inv = event.getPlayer().getInventory();

        if (!inv.getType().equals(InventoryType.PLAYER)) return;

        ItemUtil.cleanIllegals(inv, event.getPlayer().getName());
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "BlockDispenseArmorEvent")
    private void onDispenseEquip(BlockDispenseArmorEvent event) {
        if (!Config.antiIllegalCheckWhenItemPickupEnabled) return;

        ItemStack i = event.getItem();

        if (ItemUtil.isIllegal(i)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "EntityPickupItemEvent")
    private void onPickup(EntityPickupItemEvent event) {
        if (Surf.getInstance().isRoseStackerEnabled) return;

        if (!Config.antiIllegalCheckWhenItemPickupEnabled) return;

        ItemStack i = event.getItem().getItemStack();

        if (ItemUtil.isIllegal(i)) {
            event.setCancelled(true);
            event.getItem().remove();
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                MessageUtil.sendMessage(player, "&6You can not pick up this illegal item.");
            } else {
                MessageUtil.println(String.format(
                        "%s try to pick up an illegal item at %s",
                        event.getEntity().getName().toLowerCase(),
                        MessageUtil.locToString(event.getItem().getLocation())
                ));
            }
        }
    }
}