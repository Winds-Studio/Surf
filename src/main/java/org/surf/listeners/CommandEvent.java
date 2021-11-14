package org.surf.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.surf.Main;
import org.surf.util.Utils;

public class CommandEvent implements Listener {
	Main plugin;

	public CommandEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCMD(PlayerCommandPreprocessEvent event) {
		try {
			int spawn = plugin.getConfig().getInt("Spawn.Radius");
			Player player = event.getPlayer();
			if (!player.isOp() && player.getLocation().getBlockX() < spawn && player.getLocation().getBlockX() > -spawn
					&& player.getLocation().getBlockZ() < spawn && player.getLocation().getBlockZ() > -spawn) {
				if (event.getMessage().toLowerCase().contains("/home")) {
					event.setCancelled(true);
					Utils.sendMessage(player, plugin.getConfig().getString("Spawn.Message").replace("%r%", "" + spawn + ""));

				}
			}
			if (player.isInsideVehicle()) {
				if (event.getMessage().toLowerCase().contains("/tpa") || event.getMessage().toLowerCase().contains("/home")) {
					event.setCancelled(true);
					Utils.sendMessage(player, plugin.getConfig().getString("tp.prevent.message"));

				}
			}
		} catch (Error | Exception throwable) {

		}
	}
}