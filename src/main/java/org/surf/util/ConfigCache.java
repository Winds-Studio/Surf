package org.surf.util;

import org.surf.Main;

public class ConfigCache {

    private final static Main plugin = Main.getInstance();

    public static boolean IllegalBlockPlaceEnabled;
    public static String IllegalBlockPlaceMessage;

    public static boolean GateWayPreventCrashExploit;

    public static boolean GateWayPreventEntityEnterPortal;

    public static void loadConfig() {
        IllegalBlockPlaceEnabled = plugin.getConfig().getBoolean("IllegalBlockPlace.Enabled");
        IllegalBlockPlaceMessage = plugin.getConfig().getString("IllegalBlockPlace.Message");
        GateWayPreventCrashExploit = plugin.getConfig().getBoolean("GateWay.PreventCrashExploit");
        GateWayPreventEntityEnterPortal = plugin.getConfig().getBoolean("GateWay.PreventEntityEnterPortal");
    }
}
