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

    /*
        Sakamoto Util
    */

    // Server version, e.g. 1.20.2-R0.1-SNAPSHOT -> {"1","20","2"}
    private final static String[] serverVersion = Bukkit.getServer().getBukkitVersion()
            .substring(0, Bukkit.getServer().getBukkitVersion().indexOf("-"))
            .split("\\.");

    private final static int mcFirstVersion = Integer.parseInt(serverVersion[0]);
    private final static int majorVersion = Integer.parseInt(serverVersion[1]);
    private final static int minorVersion = serverVersion.length == 3? Integer.parseInt(serverVersion[2]) : 0;

    // > (major, minor)
    public static boolean isNewerThan(int major, int minor) {
        if (majorVersion > major) {
            return true;
        }

        return majorVersion == major && minorVersion > minor;
    }

    // == (major, minor)
    public static boolean isEqualTo(int major, int minor) {
        return majorVersion == major && minorVersion == minor;
    }

    // < (major, minor)
    public static boolean isOlderThan(int major, int minor) {
        if (majorVersion < major) {
            return true;
        }

        return majorVersion == major && minorVersion < minor;
    }

    // >= (major, minor)
    public static boolean isNewerAndEqual(int major, int minor) {
        if (majorVersion > major) {
            return true;
        }

        return majorVersion == major && minorVersion >= minor;
    }

    // <= (major, minor)
    public static boolean isOlderAndEqual(int major, int minor) {
        if (majorVersion < major) {
            return true;
        }

        return majorVersion == major && minorVersion <= minor;
    }
}
