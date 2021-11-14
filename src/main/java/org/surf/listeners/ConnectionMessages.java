package org.surf.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.surf.Main;
import org.surf.util.Utils;

import java.util.HashMap;

public class ConnectionMessages implements Listener, CommandExecutor {
	HashMap<String, Boolean> toggled = new HashMap<>();
	Main plugin;

	public ConnectionMessages(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		for (Player player : Bukkit.getOnlinePlayers()) {
			toggled.putIfAbsent(player.getUniqueId().toString(), true);
			if (toggled.get(player.getUniqueId().toString())) {
				Utils.sendMessage(player, plugin.getConfig().getString("Connection.Player-Join-Message").replace("%player%", event.getPlayer().getDisplayName()));
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (toggled.get(player.getUniqueId().toString())) {
				Utils.sendMessage(player, plugin.getConfig().getString("Connection.Player-Leave-Message").replace("%player%", event.getPlayer().getDisplayName()));
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (toggled.get(player.getUniqueId().toString())) {
				Utils.sendMessage(player, "&6Turned off connection messages");
				toggled.replace(player.getUniqueId().toString(), false);
			} else {
				Utils.sendMessage(player, "&6Turned connection messages on");
				toggled.replace(player.getUniqueId().toString(), true);
			}
		} else {
			Utils.sendMessage(sender, "&cYou must be a player");
		}
		return true;
	}
}