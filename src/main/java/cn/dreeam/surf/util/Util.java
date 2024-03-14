package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {

    public static double getTps() {
        double tps = Bukkit.getServer().getTPS()[0];
        return Double.parseDouble(String.format("%.2f", tps));
    }

    public static void sendMessage(Player player, String message) {
        Surf.getInstance().adventure().player(player).sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        println(player, message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        Surf.getInstance().adventure().sender(sender).sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    public static void kickPlayer(Player player, String message) {
        player.kickPlayer(message);
    }

    public static void sendOpMessage(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp()) {
                Surf.getInstance().adventure().player(online).sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
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
        return (Surf.config.Prefix() != null && !Surf.config.Prefix().isEmpty()) ? Surf.config.Prefix() : "";
    }

    public static void println(Player player, String message) {
        if (player != null) {
            message = player.getName() + " | " + message;
        }

        println(message);
    }

    public static void println(Block block, String message) {
        println(message);
    }

    public static void println(String message) {
        Surf.getInstance().adventure().console().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }
}
