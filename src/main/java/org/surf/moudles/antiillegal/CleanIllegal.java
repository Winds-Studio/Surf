package org.surf.moudles.antiillegal;

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
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import org.surf.Main;

public class CleanIllegal implements Listener {
	Main plugin;

	public CleanIllegal(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	@AntiIllegal(EventName = "BlockPlaceEvent")
	public void onPlace(BlockPlaceEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.BlockPlace-Enabled")) {
				if (plugin.getItemUtils().isIllegal(event.getItemInHand())) {
					event.setCancelled(true);
					event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
				}
			}

		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "ChunkLoadEvent")
	public void onLoad(ChunkLoadEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled")) {
				for (BlockState state : event.getChunk().getTileEntities()) {
					if (state instanceof Container) {
						Container container = (Container) state;
						plugin.getItemUtils().deleteIllegals((PlayerInventory) container.getInventory());

					}
				}
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "InventoryMoveItemEvent")
	public void onInventoryClose(InventoryMoveItemEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.HopperTransfer-Enabled")) {
				Inventory inv = event.getSource();
				if (inv.getContents() != null) {
					for (ItemStack item : inv.getStorageContents()) {
						if (item != null) {
							if (plugin.getItemUtils().isArmor(item) || plugin.getItemUtils().isTool(item)) {
								if (item.getDurability() > item.getType().getMaxDurability()) {
									item.setDurability(item.getType().getMaxDurability());
								}
								if (item.getDurability() < 0) {
									item.setDurability((short) 1);
								}

							}
							if (plugin.getItemUtils().isIllegal(item)) {
								inv.remove(item);
								event.setCancelled(true);
							}
							if (plugin.getItemUtils().hasIllegalNBT(item)) {
								inv.remove(item);
								event.setCancelled(true);
							}
//							if (plugin.getItemUtils().hasIllegalAttributes(item)) {
//								inv.remove(item);
//								event.setCancelled(true);
//							}
							if (plugin.getItemUtils().hasIllegalEnchants(item)) {
								inv.remove(item);
								event.setCancelled(true);
							}
							if (item.hasItemMeta()) {
								ItemMeta meta = item.getItemMeta();
								if (plugin.getItemUtils().isEnchantedBlock(item)) {
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "InventoryCloseEvent")
	public void onInventoryClose(InventoryCloseEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.InventoryClose-Enabled")) {
				Inventory inv = event.getInventory();
				plugin.getItemUtils().deleteIllegals(inv);
				Inventory playerInv = event.getPlayer().getInventory();
				plugin.getItemUtils().deleteIllegals(playerInv);
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "InventoryOpenEvent")
	public void onInventoryOpen(InventoryOpenEvent event) {
		try {
			Inventory inv = event.getInventory();
			if (plugin.getConfig().getBoolean("Antiillegal.InventoryOpen-Enabled")) {
				plugin.getItemUtils().deleteIllegals(inv);
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "PlayerPickupItemEvent")
	public void onPickup(PlayerPickupItemEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.ItemPickup-Enabled")) {
				ItemStack item = event.getItem().getItemStack();
				if (plugin.getItemUtils().isEnchantedBlock(item) || plugin.getItemUtils().hasIllegalNBT(item) || plugin.getItemUtils().hasIllegalEnchants(item)
						|| plugin.getItemUtils().isIllegal(item)) {
					event.setCancelled(true);
					event.getItem().remove();
				}
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "PlayerItemHeldEvent")
	public void onItemMove(PlayerItemHeldEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.HotbarMove-Enabled")) {
				Player player = event.getPlayer();
				plugin.getItemUtils().deleteIllegals(player.getInventory());
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	@AntiIllegal(EventName = "PlayerItemHeldEvent")
	public void onScroll(PlayerItemHeldEvent event) {
		for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
			if (plugin.getConfigBoolean("Antiillegal.Delete-Stacked-Totem")) {
				if (itemStack != null) {
					if (itemStack.getType() == Material.TOTEM) {
						if (itemStack.getAmount() > itemStack.getMaxStackSize()) {
							itemStack.setAmount(itemStack.getMaxStackSize());
						}
					}
				}
			}
		}
	}

	@EventHandler
	@AntiIllegal(EventName = "PlayerSwapHandItemsEvent")
	public void onSwapItem(PlayerSwapHandItemsEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.PlayerSwapOffhand-Enabled")) {
				Player player = event.getPlayer();
				plugin.getItemUtils().deleteIllegals(player.getInventory());
			}
			for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
				if (plugin.getConfigBoolean("Antiillegal.Delete-Stacked-Totem")) {
					if (itemStack != null) {
						if (itemStack.getType() == Material.TOTEM) {
							if (itemStack.getAmount() > itemStack.getMaxStackSize()) {
								event.setCancelled(true);
							}
						}
					}
				}
			}
		} catch (Error | Exception throwable) {

		}
	}
}