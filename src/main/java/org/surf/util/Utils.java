package org.surf.util;

import io.papermc.lib.PaperLib;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.surf.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Utils {
	private static Main plugin;

	public Utils(Main plugin) {
		Utils.plugin = plugin;
	}

	public static double getTps() {
		if (PaperLib.isPaper()) {
			return (Math.round(Bukkit.getServer().getTPS()[0]));
		} else {
			plugin.getLogger().log(Level.SEVERE, "Surf dose not compatible with " + getServerBrand() + " please upgrade to Paper");
			PaperLib.suggestPaper(plugin);
			return 20;
		}
	}

	public static void sendMessage(Player player, String string) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
	}

	public static void sendMessage(CommandSender sender, String string) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
	}

	public static void kickPlayer(Player player, String string) {
		player.kickPlayer(ChatColor.translateAlternateColorCodes('&', string));
	}

	public static void sendOpMessage(String message) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			if (online.isOp()) {
				online.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

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
		return "&6&l[&b&lSurf&6&l]&6 ";
	}

	public static void println(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', message));

	}

	public static void secondPass(HashMap<Player, Integer> hashMap) {
		for (Map.Entry<Player, Integer> violationEntry : hashMap.entrySet()) {
			if (violationEntry.getValue() > 0)
				violationEntry.setValue(violationEntry.getValue() - 1);
		}
	}
	public static String getServerBrand() {
		if (!PaperLib.isSpigot() && !PaperLib.isPaper()) {
			return "CraftBukkit";
		} else {
			return (PaperLib.isPaper()) ? "Paper" : "Spigot";
		}
	}
}
