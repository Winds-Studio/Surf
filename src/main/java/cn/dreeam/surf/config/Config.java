package cn.dreeam.surf.config;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;

import java.util.Arrays;
import java.util.List;

public class Config {

    // Prefix
    public static String prefix;

    // Anti Illegal
    public static boolean antiIllegalCheckIllegalBlockEnabled, checkIllegalDamageEnabled, checkIllegalPotionEnabled, stackedTotemRevertAsOneEnabled, antiIllegalDeleteIllegalsWhenFoundEnabled, antiIllegalCheckWhenPlayerJoinEnabled, antiIllegalCheckWhenHopperTransferEnabled, antiIllegalCheckWhenInventoryCloseEnabled, antiIllegalCheckWhenInventoryOpenEnabled, antiIllegalCheckWhenItemPickupEnabled;
    public static String antiIllegalCheckIllegalBlockMessage, checkIllegalDamageMessage, checkIllegalPotionMessage;
    public static List<String> antiIllegalIllegalBlockList, antiIllegalIllegalItemFlagList, antiIllegalIllegalEnchantList, antiIllegalIllegalAttributeModifierList;

    // Anti Lag
    public static boolean limitLiquidSpreadEnabled, limitVehicleEnabled, limitOffhandSwapEnabled, limitWitherSpawnOnLagEnabled;
    public static int limitLiquidSpreadDisableTPS, limitVehicleDisableTPS, limitVehicleMinecartPerChunkLimit, limitWitherSpawnOnLagDisableTPS;
    public static String limitOffhandSwapMessage;

    // Misc / Connection / Nether
    public static boolean connectionMessageEnabled, connectionFirstJoinEnabled, connectionPreventKickEnabled, netherEnabled, netherTopBottomDoDamage;
    public static int netherTopLayer, netherBottomLayer;
    public static String connectionPlayerJoin, connectionPlayerLeave, connectionFirstJoinMessage, netherTopMessage, netherBottomMessage;
    public static List<String> connectionKickReasons;

    // Patch
    public static boolean preventBookBanEnabled, preventBuketPortalEnabled, perChunkLimitEnabled, preventDispenserCrash, gateWayPreventCrashExploit, gateWayPreventEntityEnterPortal, preventNBTBanEnabled, preventTeleportToBlock;
    public static int perChunkLimitTitleEntityMax, perChunkLimitSkullMax, preventNBTBanLimit;
    public static String preventBookBanMessage, preventBuketPortalMessage, perChunkLimitMessage, preventNBTBanMessage;

    public static void initConfig() {
        // Prefix
        prefix = Surf.configManager().getString("Prefix", "&6&l[&b&lSurf&6&l]&6 ", "Message prefix");

        // Anti Illegal
        antiIllegalCheckIllegalBlockEnabled = Surf.configManager().getBoolean("anti-illegal.check-illegal-block.enabled", true);
        antiIllegalCheckIllegalBlockMessage = Surf.configManager().getString("anti-illegal.check-illegal-block.message", "&6You can not use this illegal block.");

        checkIllegalDamageEnabled = Surf.configManager().getBoolean("check-illegal-damage.enabled", false);
        checkIllegalDamageMessage = Surf.configManager().getString("check-illegal-damage.message", "&6You can not use this illegal item.");

        checkIllegalPotionEnabled = Surf.configManager().getBoolean("check-illegal-potion.enabled", false);
        checkIllegalPotionMessage = Surf.configManager().getString("check-illegal-potion.message", "&6You can not use this illegal potion.");

        stackedTotemRevertAsOneEnabled = Surf.configManager().getBoolean("anti-illegal.revert-stacked-totem-as-one.enabled", false);

        antiIllegalDeleteIllegalsWhenFoundEnabled = Surf.configManager().getBoolean("anti-illegal.delete-illegals-when-found.enabled", false, """
                Enable to delete illegals when found
                Disable to only clean illegal attributes""");

        antiIllegalCheckWhenPlayerJoinEnabled = Surf.configManager().getBoolean("anti-illegal.check-when.PlayerJoin", false);
        antiIllegalCheckWhenHopperTransferEnabled = Surf.configManager().getBoolean("anti-illegal.check-when.HopperTransfer", false);
        antiIllegalCheckWhenInventoryCloseEnabled = Surf.configManager().getBoolean("anti-illegal.check-when.InventoryClose", false);
        antiIllegalCheckWhenInventoryOpenEnabled = Surf.configManager().getBoolean("anti-illegal.check-when.InventoryOpen", false);
        antiIllegalCheckWhenItemPickupEnabled = Surf.configManager().getBoolean("anti-illegal.check-when.ItemPickup", false);

        antiIllegalIllegalBlockList = Surf.configManager().getList("anti-illegal.checks.illegal-block-list", ItemUtil.illegalBlocks);
        antiIllegalIllegalItemFlagList = Surf.configManager().getList("anti-illegal.checks.illegal-item-flag-list", ItemUtil.illegalItemFlags);
        antiIllegalIllegalEnchantList = Surf.configManager().getList("anti-illegal.checks.illegal-enchant-list", ItemUtil.illegalEnchants, """
                Set the value to -1 or remove the entire enchant from the list
                to disable check to that illegal enchant""");
        antiIllegalIllegalAttributeModifierList = Surf.configManager().getList("anti-illegal.checks.illegal-attribute-modifier-list", ItemUtil.illegalAttributes);

        // Anti Lag
        limitLiquidSpreadEnabled = Surf.configManager().getBoolean("limit.liquid-spread.enabled", false, """
                water / lava flowing disable tps this is useful on new servers with lots of block physics updates that cause lag
                Set -1 to disable""");
        limitLiquidSpreadDisableTPS = Surf.configManager().getInt("limit.liquid-spread.disable-tps", 18);

        limitVehicleEnabled = Surf.configManager().getBoolean("limit.vehicle.enabled", false);
        limitVehicleDisableTPS = Surf.configManager().getInt("limit.vehicle.disable-tps", 18);

        limitVehicleMinecartPerChunkLimit = Surf.configManager().getInt("limit.vehicle.minecart-per-chunk", 500, "Amount of vehicles allowed per chunk");

        limitOffhandSwapEnabled = Surf.configManager().getBoolean("limit.offhand-swap.enabled", false);
        limitOffhandSwapMessage = Surf.configManager().getString("limit.offhand-swap.message", "&6You swap offhand too quick!");

        limitWitherSpawnOnLagEnabled = Surf.configManager().getBoolean("limit.wither-spawn.enabled", false);

        limitWitherSpawnOnLagDisableTPS = Surf.configManager().getInt("limit.wither-spawn.disable-tps", 18);

        // Misc / Connection / Nether
        connectionMessageEnabled = Surf.configManager().getBoolean("connection-meesage.enabled", false, """
                These are the connection messages for when a player joins / leaves
                Use & for colours and %player% as a placeholder for the players name""");

        connectionPlayerJoin = Surf.configManager().getString("connection-meesage.player-join", "&7[&a+&7] &8%player%");
        connectionPlayerLeave = Surf.configManager().getString("connection-meesage.player-leave", "&7[&c-&7] &8%player%");

        connectionFirstJoinEnabled = Surf.configManager().getBoolean("connection-meesage.player-first-join.enabled", false);
        connectionFirstJoinMessage = Surf.configManager().getString("connection-meesage.player-first-join.message", "&c%player%&6 has joined the &bYour&3Server &6for the first time");

        connectionPreventKickEnabled = Surf.configManager().getBoolean("connection-prevent-kick.enabled", true);

        connectionKickReasons = Surf.configManager().getList("connection-prevent-kick.reasons", Arrays.asList(
                "Kicked for spamming",
                "Invalid hotbar selection (Hacking?)",
                "You released use item too quickly (Hacking?)"
        ));

        netherEnabled = Surf.configManager().getBoolean("nether.enabled", false, "Enable to prevent player go to Nether top or bottom layer");
        netherTopLayer = Surf.configManager().getInt("nether.top-layer", 127);
        netherBottomLayer = Surf.configManager().getInt("nether.bottom-layer", 0);
        netherTopMessage = Surf.configManager().getString("nether.top-message", "&6The nether top has been disabled due to lag");
        netherBottomMessage = Surf.configManager().getString("nether.bottom-message", "&6The nether bottom has been disabled due to lag");
        netherTopBottomDoDamage = Surf.configManager().getBoolean("nether.top-bottom-do-damage", false);

        // Patch
        preventBookBanEnabled = Surf.configManager().getBoolean("prevent-book-ban.enabled", false);
        preventBookBanMessage = Surf.configManager().getString("prevent-book-ban.message", "&6Detected a book ban, successfully cancelled.");

        preventBuketPortalEnabled = Surf.configManager().getBoolean("prevent-buket-on-portal.enabled", false);
        preventBuketPortalMessage = Surf.configManager().getString("prevent-buket-on-portal.message", "&6You can not do this");

        perChunkLimitEnabled = Surf.configManager().getBoolean("per-chunk-limit.enabled", false, "ChunkBan skull limit tile entity limit and prevent message");
        perChunkLimitMessage = Surf.configManager().getString("per-chunk-limit.message", "&6Detected a chunk ban, successfully cancelled.");
        perChunkLimitTitleEntityMax = Surf.configManager().getInt("per-chunk-limit.tile-entity-max", 500);
        perChunkLimitSkullMax = Surf.configManager().getInt("per-chunk-limit.skull-max", 100);

        preventDispenserCrash = Surf.configManager().getBoolean("prevent-dispenser-crash.enabled", true);

        gateWayPreventCrashExploit = Surf.configManager().getBoolean("gate-way.prevent-crash-exploit", false);
        gateWayPreventEntityEnterPortal = Surf.configManager().getBoolean("gate-way.prevent-entity-enter-portal", false);

        preventNBTBanEnabled = Surf.configManager().getBoolean("prevent-nbt-ban.enabled", true);
        preventNBTBanLimit = Surf.configManager().getInt("prevent-nbt-ban.nbt-limit", 100000);
        preventNBTBanMessage = Surf.configManager().getString("prevent-nbt-ban.message", "&6Detected a nbt ban, successfully cancelled.");

        preventTeleportToBlock = Surf.configManager().getBoolean("prevent-teleport-to-block.enabled", true, """
                Prevent player uses ender pearl to teleport to inside of block,
                Enable this to let PVP more friendly.""");
    }
}
