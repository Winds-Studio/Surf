package org.surf.util;

import io.papermc.lib.PaperLib;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.surf.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			plugin.getLogger().log(Level.SEVERE, "LeeesExploitFixer dose not work correctly on " + getServerBrand() + " please upgrade to paper");
			PaperLib.suggestPaper(plugin);
			return 20;
		}
	}

	public static ChatColor getTPSColor(String input) {
		if (!input.equals("*20")) {
			double i = Double.parseDouble(input);
			if (i >= 18.0D) {
				return ChatColor.GREEN;
			} else {
				return i >= 13.0D && i < 18.0D ? ChatColor.YELLOW : ChatColor.RED;
			}
		} else {
			return ChatColor.GREEN;
		}
	}

	public static void crashPlayer(Player player) {
		for (int i = 0; i < 100; i++) {
			player.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), Integer.MAX_VALUE, 1, 1, 1);
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

	public static void teleportPlayer(Player player, int x, int y, int z) {
		player.teleport(new Location(player.getWorld(), x, y, z));
	}

	public static void teleportPlayer(Player player, double x, double y, double z) {
		player.teleport(new Location(player.getWorld(), x, y, z));
	}

	public static void sendClickableMessage(Player player, String message, String hoverText, String cmd, ClickEvent.Action action) {
		TextComponent msg = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
		msg.setClickEvent(new ClickEvent(action, cmd));
		msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', hoverText))
						.create()));
		player.spigot().sendMessage(msg);
	}

	public static List<String> runSysCommand(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			List<String> output = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				output.add(line);
			}
			reader.close();
			return output;
		} catch (IOException ignored) {
			return null;
		}
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

	public static String getFormattedInterval(long ms) {
		long seconds = ms / 1000L % 60L;
		long minutes = ms / 60000L % 60L;
		long hours = ms / 3600000L % 24L;
		long days = ms / 86400000L;
		return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, seconds);
	}

	public static String getPrefix() {
		return "&6&l[&b&lLEF&6&l]&6 ";
	}

	public static void deleteFortressDat(String worldName) {
		Utils.println(Utils.getPrefix() + "&aStarting to delete files that cause memory issues...");
		String nether = worldName.concat("_nether");
		String end = worldName.concat("_the_end");
		File fortress = new File(nether + "/data/Fortress.dat");
		File villagesNether = new File(nether + "/data/villages_nether.dat");
		if (fortress.delete()) {
			Utils.println(Utils.getPrefix() + "&eDeleted file " + fortress.getName());
		} else {
			Utils.println(Utils.getPrefix() + "&cCould not find file " + fortress.getPath());
		}
		if (villagesNether.delete()) {
			Utils.println(Utils.getPrefix() + "&eDeleted file " + villagesNether.getName());
		} else {
			Utils.println(Utils.getPrefix() + "&cCould not find file " + villagesNether.getPath());
		}
		File endCity = new File(end + "/data/EndCity.dat");
		File villagesEnd = new File(end + "/data/villages_end.dat");
		if (endCity.delete()) {
			Utils.println(Utils.getPrefix() + "&eDeleted file " + endCity.getName());
		} else {
			Utils.println(Utils.getPrefix() + "&cCould not find file " + endCity.getPath());
		}
		if (villagesEnd.delete()) {
			Utils.println(Utils.getPrefix() + "&eDeleted file " + villagesEnd.getName());
		} else {
			Utils.println(Utils.getPrefix() + "&cCould not find file " + villagesEnd.getPath());
		}
		File village = new File(worldName + "/data/Village.dat");
		File villages = new File(worldName + "/data/villages.dat");
		if (village.delete()) {
			Utils.println(Utils.getPrefix() + "&eDeleted file " + village.getName());
		} else {
			Utils.println(Utils.getPrefix() + "&cCould not find file " + village.getPath());
		}
		if (villages.delete()) {
			Utils.println(Utils.getPrefix() + "&eDeleted file " + villages.getName());
		} else {
			Utils.println(Utils.getPrefix() + "&cCould not find file " + villages.getPath());
		}
		Utils.println(Utils.getPrefix() + "&aDeletion process complete!");
	}

	public static void cockRunMcCommand(String cmd) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);

	}

	public static void println(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', message));

	}

	public static void sendPlayerToServer(Player player, String server) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("Connect");
			out.writeUTF(server);
			player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
			b.close();
			out.close();
		} catch (Exception | Error e) {
			player.sendMessage(ChatColor.RED + "Error when trying to connect to " + server);
		}
	}

	public static int countBlockPerChunk(Chunk chunk, Material lookingFor) {
		int count = 0;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (chunk.getBlock(x, y, z).getType() == lookingFor)
						count++;
				}
			}
		}
		return count;
	}

	public static void changeBlockInChunk(Chunk chunk, Material target, Material to) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 256; y++) {
					if (chunk.getBlock(x, y, z).getType() == target) {
						chunk.getBlock(x, y, z).setType(to);
					}
				}
			}
		}
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