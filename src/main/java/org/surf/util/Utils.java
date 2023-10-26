package org.surf.util;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

    public static double getTps() {
		return Math.round(Bukkit.getServer().getTPS()[0]);
	}

	public static void sendMessage(Player player, String string) {
		player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(string));
	}

	public static void sendMessage(CommandSender sender, String string) {
		sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(string));
	}

	public static void kickPlayer(Player player, String string) {
		player.kick(LegacyComponentSerializer.legacyAmpersand().deserialize(string));
	}

	public static void sendOpMessage(String message) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			if (online.isOp()) {
				online.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
			}
		}
	}

	public static Player getNearbyPlayer(int i, Location loc) {
		Player plrs = null;
		for (Player nearby : loc.getNearbyPlayers(i)) {
			plrs = nearby;
		}
		return plrs;
	}

	public static String getPrefix() {
		return ConfigCache.Prefix;
	}

	public static void println(String message) {
		System.out.println(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
	}
}
