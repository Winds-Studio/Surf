package cn.dreeam.surf.config;

import cn.dreeam.surf.util.ItemUtil;
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
        ItemUtil.loadIllegalMaterials();

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
    @ConfKey("prevent-book-ban.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean preventBookBanEnabled();

    @ConfKey("prevent-book-ban.message")
    @ConfDefault.DefaultString("&6You have been unbookbanned")
    String preventBookBanMessage();

    @ConfKey("prevent-buket-on-portal.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean preventBuketPortalEnabled();

    @ConfKey("prevent-buket-on-portal.message")
    @ConfDefault.DefaultString("&6You can't do this")
    String preventBuketPortalMessage();

    @ConfKey("per-chunk-limit.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean perChunkLimitEnabled();

    @ConfKey("per-chunk-limit.message")
    @ConfDefault.DefaultString("&6ChunkBan has been disabled")
    String perChunkLimitMessage();

    @ConfKey("per-chunk-limit.tile-entity-max")
    @ConfDefault.DefaultInteger(500)
    int perChunkLimitTitleEntityMax();

    @ConfKey("per-chunk-limit.skull-max")
    @ConfDefault.DefaultInteger(100)
    int perChunkLimitSkullMax();

    @ConfKey("prevent-dispenser-crash.enabled")
    @ConfDefault.DefaultBoolean(true)
    boolean preventDispenserCrash();

    @ConfKey("gate-way.prevent-crash-exploit")
    @ConfDefault.DefaultBoolean(false)
    boolean gateWayPreventCrashExploit();

    @ConfKey("gate-way.prevent-entity-enter-portal")
    @ConfDefault.DefaultBoolean(false)
    boolean gateWayPreventEntityEnterPortal();

    @ConfKey("prevent-nbt-ban.enabled")
    @ConfDefault.DefaultBoolean(true)
    boolean preventNBTBanEnabeld();

    @ConfKey("prevent-nbt-ban.nbt-limit")
    @ConfDefault.DefaultInteger(85000)
    int preventNBTBanLimit();

    @ConfKey("prevent-nbt-ban.message")
    @ConfDefault.DefaultString("&6 You have been un-NBT-banned")
    String preventNBTBanMessage();
}

