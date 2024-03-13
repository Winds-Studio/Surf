package cn.dreeam.surf.config;

import cn.dreeam.surf.util.ItemUtils;
import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfHeader;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.List;

@ConfHeader({
        "Surf 5.0.0",
        "# Contact me on QQ:2682173972 or Discord: dreeam___",
        "# For help with this plugin"
})
public interface Config {

    // Prefix
    @ConfComments({
            "Message prefix"
    })
    @ConfKey("Prefix")
    @ConfDefault.DefaultString("&6&l[&b&lSurf&6&l]&6 ")
    @AnnotationBasedSorter.Order(1)
    String Prefix();

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

    CheckIllegalDamage = plugin.getConfig().getBoolean("Check-Illegal-Damage");
    IllegalDamageMessage = plugin.getConfig().getString("IllegalDamage.Message");
    IllegalPotionMessage = plugin.getConfig().getString("IllegalPotion.Message");

    // Anti Lag
    LimitLiquidSpreadEnabled = plugin.getConfig().getBoolean("LimitLiquidSpread.Enabled");
    LimitLiquidSpreadDisableTPS = plugin.getConfig().getInt("LimitLiquidSpread.disable-tps");

    LimitVehicleEnabled = plugin.getConfig().getBoolean("LimitVehicle.Enabled");
    LimitVehicleDisableTPS = plugin.getConfig().getInt("LimitVehicle.disable-tps");
    MinecartPerChunkLimit = plugin.getConfig().getInt("LimitVehicle.Minecart-per-chunk.limit");

    Offhand;

    LimitWitherSpawnOnLagEnabled = plugin.getConfig().getBoolean("LimitWitherSpawnOnLag.Enabled");
    LimitWitherSpawnOnLagDisableTPS = plugin.getConfig().getInt("LimitWitherSpawnOnLag.disable-tps");

    // Misc / Connection / Nether
    @ConfComments({
            "These are the connection messages for when a player joins / leaves",
            "Use & for colours and %player% as a placeholder for the players name"
    })
    @ConfKey("connection-meesage.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean connectionMessageEnabled();

    @ConfKey("connection-meesage.player-join")
    @ConfDefault.DefaultString("&7[&a+&7] &8%player%")
    String connectionPlayerJoin();

    @ConfKey("connection-meesage.player-leave")
    @ConfDefault.DefaultString("&7[&c-&7] &8%player%")
    String connectionPlayerLeave();

    @ConfKey("connection-meesage.player-first-join.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean connectionFirstJoinEnabled();

    @ConfKey("connection-meesage.player-first-join.message")
    @ConfDefault.DefaultString("&c%player%&6 has joined the &bYour&3Server &6for the first time")
    String connectionFirstJoinMessage();

    @ConfKey("connection-prevent-kick.enabled")
    @ConfDefault.DefaultBoolean(true)
    boolean connectionPreventKickEnabled();

    @ConfKey("connection-prevent-kick.reasons")
    @ConfDefault.DefaultStrings({
            "Kicked for spamming",
            "Invalid hotbar selection (Hacking?)",
            "You released use item too quickly (Hacking?)"
    })
    List<String> connectionKickReasons();

    @ConfComments({
            "Enable to prevent player go to Nether top or bottom layer"
    })
    @ConfKey("nether.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean netherEnabled();

    @ConfKey("nether.top-layer")
    @ConfDefault.DefaultInteger(127)
    int netherTopLayer();

    @ConfKey("nether.bottom-layer")
    @ConfDefault.DefaultInteger(0)
    int netherBottomLayer();

    @ConfKey("nether.top-message")
    @ConfDefault.DefaultString("&6The nether top has been disabled due to lag")
    String netherTopMessage();

    @ConfKey("nether.bottom-message")
    @ConfDefault.DefaultString("&6The nether bottom has been disabled due to lag")
    String netherBottomMessage();

    @ConfKey("nether.top-bottom-do-damage")
    @ConfDefault.DefaultBoolean(false)
    boolean netherTopBottomDoDamage();

    // Patches
    BookBan;
    BucketEvent;

    ChunkBanEnabled = plugin.getConfig().getBoolean("ChunkBan.Enabled");
    ChunkBanTileEntityMax = plugin.getConfig().getInt("ChunkBan.TileEntity-Max");
    ChunkBanPreventMessage = plugin.getConfig().getString("ChunkBan.Prevent-Message");
    ChunkBanSkullMax = plugin.getConfig().getInt("ChunkBan.Skull-Max");

    DispenserCrash;

    GateWayPreventCrashExploit = plugin.getConfig().getBoolean("GateWay.PreventCrashExploit");
    GateWayPreventEntityEnterPortal = plugin.getConfig().getBoolean("GateWay.PreventEntityEnterPortal");

    AntiNBTBanEnabeld = plugin.getConfig().getBoolean("AntiNBTBan.Enabled");
    AntiNBTBanLimit = plugin.getConfig().getInt("AntiNBTBan.NBT-limit");
    AntiNBTBanMessage = plugin.getConfig().getString("AntiNBTBan.Message");
}

