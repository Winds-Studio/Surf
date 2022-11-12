package org.surf.util;

import org.surf.Main;

public class ConfigCache {

    private final static Main plugin = Main.getInstance();

    public static boolean IllegalBlockPlaceEnabled;
    public static String IllegalBlockPlaceMessage;

    public static boolean GateWayPreventCrashExploit;
    public static boolean GateWayPreventEntityEnterPortal;

    public static boolean ChunkBanEnabled;
    public static int ChunkBanTileEntityMax;
    public static String ChunkBanPreventMessage;
    public static int ChunkBanSkullMax;

    public static void loadConfig() {
        IllegalBlockPlaceEnabled = plugin.getConfig().getBoolean("IllegalBlockPlace.Enabled");
        IllegalBlockPlaceMessage = plugin.getConfig().getString("IllegalBlockPlace.Message");
        GateWayPreventCrashExploit = plugin.getConfig().getBoolean("GateWay.PreventCrashExploit");
        GateWayPreventEntityEnterPortal = plugin.getConfig().getBoolean("GateWay.PreventEntityEnterPortal");
        ChunkBanEnabled = plugin.getConfig().getBoolean("ChunkBan.Enabled");
        ChunkBanTileEntityMax = plugin.getConfig().getInt("ChunkBan.TileEntity-Max");
        ChunkBanPreventMessage = plugin.getConfig().getString("ChunkBan.Prevent-Message");
        ChunkBanSkullMax = plugin.getConfig().getInt("ChunkBan.Skull-Max");
    }
}
