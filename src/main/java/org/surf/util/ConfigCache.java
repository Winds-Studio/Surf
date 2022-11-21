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

    public static boolean NetherEnabled;
    public static int NetherTopLayer;
    public static int NetherBottomLayer;
    public static String NetherTopMessage;
    public static String NetherBottomMessage;
    public static boolean NetherTopBottomDoDamage;

    public static boolean AntiIllegalCheckIllegalDamage;
    public static String IllegalDamageMessage;
    public static String IllegalPotionMessage;

    public static void loadConfig() {
        IllegalBlockPlaceEnabled = plugin.getConfig().getBoolean("IllegalBlockPlace.Enabled");
        IllegalBlockPlaceMessage = plugin.getConfig().getString("IllegalBlockPlace.Message");
        GateWayPreventCrashExploit = plugin.getConfig().getBoolean("GateWay.PreventCrashExploit");
        GateWayPreventEntityEnterPortal = plugin.getConfig().getBoolean("GateWay.PreventEntityEnterPortal");
        ChunkBanEnabled = plugin.getConfig().getBoolean("ChunkBan.Enabled");
        ChunkBanTileEntityMax = plugin.getConfig().getInt("ChunkBan.TileEntity-Max");
        ChunkBanPreventMessage = plugin.getConfig().getString("ChunkBan.Prevent-Message");
        ChunkBanSkullMax = plugin.getConfig().getInt("ChunkBan.Skull-Max");
        NetherEnabled = plugin.getConfig().getBoolean("Nether.Enabled");
        NetherTopLayer = plugin.getConfig().getInt("Nether.Top-Layer");
        NetherBottomLayer = plugin.getConfig().getInt("Nether.Bottom-Layer");
        NetherTopMessage = plugin.getConfig().getString("Nether.Top-message");
        NetherBottomMessage = plugin.getConfig().getString("Nether.Bottom-message");
        NetherTopBottomDoDamage = plugin.getConfig().getBoolean("Nether.top-bottom-do-damage");
        AntiIllegalCheckIllegalDamage = plugin.getConfig().getBoolean("Antiillegal.Check-Illegal-Damage");
        IllegalDamageMessage = plugin.getConfig().getString("IllegalDamage.Message");
        IllegalPotionMessage = plugin.getConfig().getString("IllegalPotion.Message");

    }
}
