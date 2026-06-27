package cn.dreeam.surf.listener;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheckHandler;
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

public class ListenerBasic implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onJoin(PlayerJoinEvent event) {
        if (!Config.ItemChecks.checkTriggerOnJoin) return;

        Inventory inv = event.getPlayer().getInventory();

        ItemCheckHandler.scanInv(inv, event.getPlayer().getName());
    }

    @EventHandler(ignoreCancelled = true)
    private void onInvMove(InventoryMoveItemEvent event) {
        if (!Config.ItemChecks.checkTriggerOnHopperTransfer) return;

        Inventory inv = event.getSource();

        // Only check current player inventory
        if (!inv.getType().equals(InventoryType.CRAFTING)) return;

        ItemCheckHandler.scanInv(inv, inv.getType().name());
    }

    @EventHandler
    private void onInvClose(InventoryCloseEvent event) {
        if (!Config.ItemChecks.checkTriggerOnInvClose) return;

        Inventory inv = event.getPlayer().getInventory();

        // Only check current player inventory
        if (!inv.getType().equals(InventoryType.PLAYER)) return;

        ItemCheckHandler.scanInv(inv, event.getPlayer().getName());
    }

    @EventHandler
    private void onInvOpen(InventoryOpenEvent event) {
        if (!Config.ItemChecks.checkTriggerOnInvOpen) return;

        Inventory inv = event.getPlayer().getInventory();

        // Only check current player inventory
        if (!inv.getType().equals(InventoryType.PLAYER)) return;

        ItemCheckHandler.scanInv(inv, event.getPlayer().getName());
    }

    @EventHandler(ignoreCancelled = true)
    private void onDispenseEquip(BlockDispenseArmorEvent event) {
        if (!Config.ItemChecks.checkTriggerOnPickup) return;

        ItemStack i = event.getItem();

        if (ItemCheckHandler.scanItemOrReact(i)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onPickup(EntityPickupItemEvent event) {
        if (Surf.getInstance().isRoseStackerEnabled) return;

        if (!Config.ItemChecks.checkTriggerOnPickup) return;

        ItemStack i = event.getItem().getItemStack();

        if (ItemCheckHandler.scanItemOrReact(i)) {
            event.setCancelled(true);
            event.getItem().remove();

            if (event.getEntity() instanceof Player player) {
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