package org.surf.util;

import org.surf.Main;
import org.surf.modules.antiillegal.ItemUtils;

import java.util.List;

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

    public static int BlockPhysicsDisableTPS;

    public static int MinecartPerChunkLimit;

    public static boolean FirstJoinEnabled;
    public static String FirstJoinMessage;

    public static boolean ConnectionEnabled;
    public static String ConnectionPlayerJoinMessage;
    public static String ConnectionPlayerLeaveMessage;

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

    public static int IllegalEnchantsThreshold;

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
        BlockPhysicsDisableTPS = plugin.getConfig().getInt("BlockPhysics-disable-tps");
        MinecartPerChunkLimit = plugin.getConfig().getInt("Minecart-per-chunk.limit");
        FirstJoinEnabled = plugin.getConfig().getBoolean("FirstJoin.Enabled");
        FirstJoinMessage = plugin.getConfig().getString("FirstJoin.Message");
        ConnectionEnabled = plugin.getConfig().getBoolean("Connection.Enabled");
        ConnectionPlayerJoinMessage = plugin.getConfig().getString("Connection.Player-Join-Message");
        ConnectionPlayerLeaveMessage = plugin.getConfig().getString("Connection.Player-Leave-Message");
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
        IllegalEnchantsThreshold = plugin.getConfig().getInt("IllegalEnchants.Threshold");
    }
}
