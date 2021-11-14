package org.surf.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
			if (plugin.getConfigBoolean("IllegalBlock-Place.Enabled")) {
				switch (event.getBlock().getType()) {
					case BEDROCK:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlock-Place.Bedrock"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case ENDER_PORTAL_FRAME:
						if (!(player.getInventory().getItemInMainHand().getType() == Material.EYE_OF_ENDER)) {
							if (!(player.getInventory().getItemInOffHand().getType() == Material.EYE_OF_ENDER)) {
								event.setCancelled(true);
								Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlock-Place.End_Portal_Frame"));
								event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
							}
						}
						break;
					case BARRIER:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlock-Place.Barrier"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case MOB_SPAWNER:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlock-Place.Mob_Spawner"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
				}
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		try {
			if (event.getBlock().getType() == Material.BEDROCK) {
				event.setCancelled(true);
			}
		} catch (Error | Exception throwable) {

		}
	}
}