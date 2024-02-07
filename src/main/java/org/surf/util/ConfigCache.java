package org.surf.util;

import org.surf.Main;
import org.surf.modules.antiillegal.ItemUtils;

import java.util.List;

public class ConfigCache {

    private final static Main plugin = Main.getInstance();

    public static String Prefix;

    // Anti Illegal
    public static boolean AntiillegalBlockPlaceEnabled;
    public static boolean AntiillegalChunkLoadEnabled;
    public static boolean AntiillegalHopperTransferEnabled;
    public static boolean AntiillegalInventoryCloseEnabled;
    public static boolean AntiillegalInventoryOpenEnabled;
    public static boolean AntiillegalItemPickupEnabled;
    public static boolean AntiillegalHotbarMoveEnabled;
    public static boolean AntiillegalDeleteStackedTotem;
    public static boolean AntiillegalPlayerSwapOffhandEnabled;
    public static boolean AntiillegalPlayerInteractEnabled;
    public static List<String> AntiillegalIllegalItemsList;

    public static boolean IllegalBlockPlaceEnabled;
    public static String IllegalBlockPlaceMessage;
    public static int IllegalEnchantsThreshold;

    public static boolean AntiIllegalCheckIllegalDamage;
    public static String IllegalDamageMessage;
    public static String IllegalPotionMessage;

    // Anti Lag
    public static boolean LimitLiquidSpreadEnabled;
    public static int LimitLiquidSpreadDisableTPS;

    public static boolean LimitVehicleEnabled;
    public static int LimitVehicleDisableTPS;
    public static int MinecartPerChunkLimit;

    public static boolean LimitWitherSpawnOnLagEnabled;
    public static int LimitWitherSpawnOnLagDisableTPS;

    // Patches
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

    // Connection
    public static boolean ConnectionEnabled;
    public static String ConnectionPlayerJoinMessage;
    public static String ConnectionPlayerLeaveMessage;
    public static boolean ConnectionPreventKickEnabled;
    public static List<String> ConnectionKickReasons;
    public static boolean FirstJoinEnabled;
    public static String FirstJoinMessage;

    public static void loadConfig() {
        Prefix = plugin.getConfig().getString("Prefix");

        // Anti Illegal
        AntiillegalBlockPlaceEnabled = plugin.getConfig().getBoolean("Antiillegal.BlockPlace-Enabled");
        AntiillegalChunkLoadEnabled = plugin.getConfig().getBoolean("Antiillegal.ChunkLoad-Enabled");
        AntiillegalHopperTransferEnabled = plugin.getConfig().getBoolean("Antiillegal.HopperTransfer-Enabled");
        AntiillegalInventoryCloseEnabled = plugin.getConfig().getBoolean("Antiillegal.InventoryClose-Enabled");
        AntiillegalInventoryOpenEnabled = plugin.getConfig().getBoolean("Antiillegal.InventoryOpen-Enabled");
        AntiillegalItemPickupEnabled = plugin.getConfig().getBoolean("Antiillegal.ItemPickup-Enabled");
        AntiillegalHotbarMoveEnabled = plugin.getConfig().getBoolean("Antiillegal.HotbarMove-Enabled");
        AntiillegalDeleteStackedTotem = plugin.getConfig().getBoolean("Antiillegal.Delete-Stacked-Totem");
        AntiillegalPlayerSwapOffhandEnabled = plugin.getConfig().getBoolean("Antiillegal.PlayerSwapOffhand-Enabled");
        AntiillegalPlayerInteractEnabled = plugin.getConfig().getBoolean("Antiillegal.PlayerInteract-Enabled");
        AntiillegalIllegalItemsList = plugin.getConfig().getStringList("Antiillegal.Illegal-Items-List");
        ItemUtils.loadIllegalMaterials();

        IllegalBlockPlaceEnabled = plugin.getConfig().getBoolean("IllegalBlockPlace.Enabled");
        IllegalBlockPlaceMessage = plugin.getConfig().getString("IllegalBlockPlace.Message");
        IllegalEnchantsThreshold = plugin.getConfig().getInt("IllegalEnchants.Threshold");

        AntiIllegalCheckIllegalDamage = plugin.getConfig().getBoolean("Antiillegal.Check-Illegal-Damage");
        IllegalDamageMessage = plugin.getConfig().getString("IllegalDamage.Message");
        IllegalPotionMessage = plugin.getConfig().getString("IllegalPotion.Message");

        // Anti Lag
        LimitLiquidSpreadEnabled = plugin.getConfig().getBoolean("LimitLiquidSpread.Enabled");
        LimitLiquidSpreadDisableTPS = plugin.getConfig().getInt("LimitLiquidSpread.disable-tps");

        LimitVehicleEnabled = plugin.getConfig().getBoolean("LimitVehicle.Enabled");
        LimitVehicleDisableTPS = plugin.getConfig().getInt("LimitVehicle.disable-tps");
        MinecartPerChunkLimit = plugin.getConfig().getInt("LimitVehicle.Minecart-per-chunk.limit");

        LimitWitherSpawnOnLagEnabled = plugin.getConfig().getBoolean("LimitWitherSpawnOnLag.Enabled");
        LimitWitherSpawnOnLagDisableTPS = plugin.getConfig().getInt("LimitWitherSpawnOnLag.disable-tps");

        // Patches
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

        // Connection
        ConnectionEnabled = plugin.getConfig().getBoolean("Connection.Enabled");
        ConnectionPlayerJoinMessage = plugin.getConfig().getString("Connection.Player-Join-Message");
        ConnectionPlayerLeaveMessage = plugin.getConfig().getString("Connection.Player-Leave-Message");
        ConnectionPreventKickEnabled = plugin.getConfig().getBoolean("Connection-Prevent-Kick.Enabled");
        ConnectionKickReasons = plugin.getConfig().getStringList("Connection-Prevent-Kick.Kick-Reasons");
        FirstJoinEnabled = plugin.getConfig().getBoolean("FirstJoin.Enabled");
        FirstJoinMessage = plugin.getConfig().getString("FirstJoin.Message");
    }
}
