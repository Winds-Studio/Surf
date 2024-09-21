package cn.dreeam.surf.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {

    @SuppressWarnings("deprecation")
    public static void sendMessage(Player player, String message) {
        if (Util.isNewerAndEqual(16, 0)) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(Util.getPrefix() + message);

            player.sendMessage(component);
        } else {
            message = ChatColor.translateAlternateColorCodes('&', Util.getPrefix() + message);

            player.sendMessage(message);
        }

        println(player + " | " + message);
    }

    @SuppressWarnings("deprecation")
    public static void sendMessage(CommandSender sender, String message) {
        if (Util.isNewerAndEqual(16, 0)) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);

            sender.sendMessage(component);
        } else {
            message = ChatColor.translateAlternateColorCodes('&', message);

            sender.sendMessage(message);
        }
    }

    @SuppressWarnings("deprecation")
    public static void println(String message) {
        message = ChatColor.translateAlternateColorCodes('&', Util.getPrefix() + message);

        Bukkit.getServer().getConsoleSender().sendMessage(message);
    }
}
