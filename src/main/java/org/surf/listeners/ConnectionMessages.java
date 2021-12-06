package org.surf.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.surf.Main;

public class ConnectionMessages implements Listener {
	Main plugin;

	public ConnectionMessages(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(plugin.getConfig().getString("Connection.Player-Join-Message").replace("%player%", event.getPlayer().getDisplayName()));
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		event.setQuitMessage(plugin.getConfig().getString("Connection.Player-Leave-Message").replace("%player%", event.getPlayer().getDisplayName()));
	}

}
