package org.surf.moudles;

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
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case ENDER_PORTAL_FRAME:
						if (!(player.getInventory().getItemInMainHand().getType() == Material.EYE_OF_ENDER)) {
							if (!(player.getInventory().getItemInOffHand().getType() == Material.EYE_OF_ENDER)) {
								event.setCancelled(true);
								Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
								event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
							}
						}
						break;
					case BARRIER:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case MOB_SPAWNER:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case COMMAND_REPEATING:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case COMMAND_MINECART:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case COMMAND_CHAIN:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case COMMAND:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case ENDER_PORTAL:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case END_GATEWAY:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case PORTAL:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case STRUCTURE_BLOCK:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case STRUCTURE_VOID:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
				}
			}
		} catch (Error | Exception throwable) {

		}
	}
}
