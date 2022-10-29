package org.surf.moudles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.surf.Main;

public class ConnectionEvent implements Listener {
	Main plugin;

	public ConnectionEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Connection.Player-Join-Message").replace("%player%", event.getPlayer().getDisplayName())));
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		try {
			Player player = event.getPlayer();
			if (player.getActivePotionEffects() != null) {
				for (PotionEffect effects : player.getActivePotionEffects()) {
					if (effects.getAmplifier() > 5 && effects.getDuration() > 12000) {
						player.removePotionEffect(effects.getType());
					}
				}

			}
			if (plugin.getConfigBoolean("FirstJoin.Enabled")) {
				if (!player.hasPlayedBefore()) {
					Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							plugin.getConfig().getString("FirstJoin.Message").replace("%Player%", player.getName())));
				}
			}
			event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Connection.Player-Leave-Message").replace("%player%", event.getPlayer().getDisplayName())));
		} catch (Error | Exception throwable) {
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getReason().equalsIgnoreCase("Kicked for spamming")) {
			event.setCancelled(true);
		}
		if (event.getReason().equalsIgnoreCase("Invalid hotbar selection (Hacking?)")) {
			event.setCancelled(true);
		}
	}
}