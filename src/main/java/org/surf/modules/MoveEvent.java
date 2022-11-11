package org.surf.modules;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.surf.Main;
import org.surf.util.Utils;

public class MoveEvent implements Listener {
	Main plugin;

	public MoveEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		try {
			Player player = event.getPlayer();
			int x = player.getLocation().getBlockX();
			int z = player.getLocation().getBlockZ();
			Location bottom = new Location(player.getWorld(), z, 5, x);
			Location top = new Location(player.getWorld(), z, 125, x);
			if (plugin.getConfig().getBoolean("Nether.Enabled") && !(player.hasPermission("surf.bypass.netherroof"))) {
				if (player.getWorld().getEnvironment() == Environment.NETHER
						&& player.getLocation().getBlockY() > plugin.getConfig().getInt("Nether.Top-Layer")) {

					player.teleport(top);
					Utils.sendMessage(player, plugin.getConfig().getString("Nether.Top-message"));
					if (plugin.getConfig().getBoolean("Nether.top-bottom-do-damage")) {
						player.damage(20);
					}
				}
				if (player.getWorld().getEnvironment() == Environment.NETHER
						&& player.getLocation().getBlockY() < plugin.getConfig().getInt("Nether.Bottom-Layer")) {

					player.teleport(bottom);
					Utils.sendMessage(player, plugin.getConfig().getString("Nether.Bottom-message"));
					if (plugin.getConfig().getBoolean("Nether.top-bottom-do-damage")) {
						player.damage(20);
					}
				}
			}
		} catch (Error | Exception throwable) {
		}
	}
}
