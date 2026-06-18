package cn.dreeam.surf.util;

import cn.dreeam.surf.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Util {

    public static double getTps() {
        double tps = Bukkit.getServer().getTPS()[0];
        return Double.parseDouble(String.format("%.2f", tps));
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
        return (Config.prefix != null && !Config.prefix.isEmpty()) ? Config.prefix : "";
    }

    public static boolean doesClassExists(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
