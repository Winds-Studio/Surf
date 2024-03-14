package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
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
        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message).replaceText(TextReplacementConfig.builder().match("%prefix%").replacement(getPrefix()).build());
        Surf.getInstance().adventure().player(player).sendMessage(component);
        println(player, message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    public static void sendMessage(CommandSender sender, Component component) {
        Surf.getInstance().adventure().sender(sender).sendMessage(component);
    }

    public static void kickPlayer(Player player, String message) {
        player.kickPlayer(message);
    }

    public static Player getNearbyPlayer(int radius, Location loc) {
        Player p = null;

        for (Player nearby : loc.getNearbyPlayers(radius)) {
            p = nearby;
        }

        return p;
    }

    public static Component getPrefix() {
        return (Surf.config.Prefix() != null && !Surf.config.Prefix().isEmpty()) ? LegacyComponentSerializer.legacyAmpersand().deserialize(Surf.config.Prefix()) : Component.empty();
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
        println(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    public static void println(Component component) {
        Surf.getInstance().adventure().console().sendMessage(component);
    }
}
