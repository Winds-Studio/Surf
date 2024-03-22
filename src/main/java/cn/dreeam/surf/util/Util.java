package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {

    public static double getTps() {
        double tps = Bukkit.getServer().getTPS()[0];
        return Double.parseDouble(String.format("%.2f", tps));
    }

    public static void sendMessage(Player player, String message) {
        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(getPrefix() + message);

        Surf.getInstance().adventure().player(player).sendMessage(component);
        println(player + " | " + message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        Surf.getInstance().adventure().sender(sender).sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    public static void kickPlayer(Player player, String message) {
        player.kickPlayer(getPrefix() + message);
    }

    public static Player getNearbyPlayer(int radius, Location loc) {
        Player p = null;

        for (Player nearby : loc.getNearbyPlayers(radius)) {
            p = nearby;
        }

        return p;
    }

    public static String getPrefix() {
        return (Surf.config.Prefix() != null && !Surf.config.Prefix().isEmpty()) ? Surf.config.Prefix() : "";
    }

    public static void println(String message) {
        println(LegacyComponentSerializer.legacyAmpersand().deserialize(getPrefix() + message));
    }

    public static void println(Component component) {
        Surf.getInstance().adventure().console().sendMessage(component);
    }
}
