package org.surf.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.potion.PotionEffect;
import org.surf.Main;
import org.surf.util.Utils;

public class JoinEvent implements Listener {
	Main plugin;

	public JoinEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		try {
			Player player = e.getPlayer();
			if (player.getActivePotionEffects() != null) {
				for (PotionEffect effects : player.getActivePotionEffects()) {
					if (effects.getAmplifier() > 5) {
						player.removePotionEffect(effects.getType());
					}
				}

			}
		} catch (Error | Exception throwable) {
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getReason().equalsIgnoreCase("Kicked for spamming")) {
			event.setCancelled(true);
		}
	}
}
