package org.surf.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.surf.Main;
import org.surf.util.Utils;

public class BlockPlace implements Listener {
	Main plugin;

	public BlockPlace(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		try {
			Player player = event.getPlayer();
			if (plugin.getConfigBoolean("IllegalBlockPlace.Enabled")) {
				switch (event.getBlock().getType()) {
					case BEDROCK:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Bedrock"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case END_PORTAL_FRAME:
						if (!(player.getInventory().getItemInMainHand().getType() == Material.ENDER_EYE)) {
							if (!(player.getInventory().getItemInOffHand().getType() == Material.ENDER_EYE)) {
								event.setCancelled(true);
								Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.End_Portal_Frame"));
								event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
							}
						}
						break;
					case BARRIER:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Barrier"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case SPAWNER:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Mob_Spawner"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
				}
			}
		} catch (Error | Exception throwable) {

		}
	}
}
