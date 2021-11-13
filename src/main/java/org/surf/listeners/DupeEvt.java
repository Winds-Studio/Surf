package org.surf.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.surf.util.Utils;
import org.l2x9.saldupe.api.PlayerDupeEvent;

import java.util.ArrayList;

public class DupeEvt implements Listener {
	@EventHandler
	public void onDupe(PlayerDupeEvent event) {
		try {
			ArrayList<Entity> items = new ArrayList<>();
			for (Entity entity : event.getPlayer().getLocation().getNearbyEntities(30, 30, 30)) {
				if (entity instanceof Item) {
					items.add(entity);
				}
			}
			if (items.size() > 20) {
				for (Entity entity : items) {
					if (entity != null) {
						entity.remove();
					}
				}
			}
		} catch (Error | Exception throwable) {
			

		}
	}

	@EventHandler
	public void onHopper(InventoryMoveItemEvent event) {
		try {
			if (Utils.getTps() < 17) {
				if (event.getSource().getType() == InventoryType.HOPPER) {
					event.setCancelled(true);
				}
			}
		} catch (Error | Exception throwable) {
			

		}
	}
}