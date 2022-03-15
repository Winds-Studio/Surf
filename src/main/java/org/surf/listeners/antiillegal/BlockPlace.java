package org.surf.listeners.antiillegal;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.surf.Main;

public class BlockPlace implements Listener {
	Main plugin;

	public BlockPlace(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	@AntiIllegal(EventName = "BlockPlaceEvent")
	public void onPlace(BlockPlaceEvent event) {
		try {
			if (plugin.getConfig().getBoolean("Antiillegal.Block-Place-Enabled")) {
				if (plugin.getItemUtils().isIllegal(event.getItemInHand())) {
					event.setCancelled(true);
					event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
				}
			}

		} catch (Error | Exception throwable) {

		}
	}
}