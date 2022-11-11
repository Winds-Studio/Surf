package org.surf.modules;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.surf.Main;
import org.surf.util.Utils;

public class BlockPlace implements Listener {
	private final Main plugin;

	public BlockPlace(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		try {
			Player player = event.getPlayer();
			if (plugin.getConfig().getBoolean("IllegalBlockPlace.Enabled")) {
				switch (event.getBlock().getType()) {
					case BEDROCK:
					case BARRIER:
					case SPAWNER:
					case REPEATING_COMMAND_BLOCK:
					case COMMAND_BLOCK_MINECART:
					case CHAIN_COMMAND_BLOCK:
					case COMMAND_BLOCK:
					case END_PORTAL:
					case END_GATEWAY:
					case NETHER_PORTAL:
					case STRUCTURE_BLOCK:
					case STRUCTURE_VOID:
					case JIGSAW:
					case LIGHT:
						event.setCancelled(true);
						Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
						event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						break;
					case END_PORTAL_FRAME:
						if (player.getInventory().getItemInMainHand().getType() == Material.END_PORTAL_FRAME || player.getInventory().getItemInOffHand().getType() == Material.END_PORTAL_FRAME) {
							event.setCancelled(true);
							Utils.sendMessage(player, plugin.getConfig().getString("IllegalBlockPlace.Message"));
							event.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
						}
						break;
				}
			}
		} catch (Error | Exception throwable) {

		}
	}
}
