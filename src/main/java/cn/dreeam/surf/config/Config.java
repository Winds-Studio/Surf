package cn.dreeam.surf.config;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.item.ItemUtil;

import java.util.Arrays;
import java.util.List;

public class Config {

    // Prefix
    public static String prefix;

    // Anti illegal
    public static boolean antiIllegalCheckIllegalBlockEnabled, antiIllegalRemoveBlockEnchant, antiIllegalAllowInapplicableEnchant, checkIllegalDamageEnabled, checkIllegalPotionEnabled, stackedTotemRevertAsOneEnabled, antiIllegalDeleteIllegalsWhenFoundEnabled, antiIllegalCheckWhenPlayerJoinEnabled, antiIllegalCheckWhenHopperTransferEnabled, antiIllegalCheckWhenInventoryCloseEnabled, antiIllegalCheckWhenInventoryOpenEnabled, antiIllegalCheckWhenItemPickupEnabled;
    public static String antiIllegalCheckIllegalBlockMessage, checkIllegalDamageMessage, checkIllegalPotionMessage;
    public static List<String> antiIllegalIllegalBlockList, antiIllegalIllegalItemFlagList, antiIllegalIllegalEnchantList, antiIllegalIllegalAttributeModifierList;

    // Item checks - general
    public static boolean checkItemAmount, checkItemDurability, checkItemEnchantments, checkItemAttributes,
            checkItemFlags, checkItemUnbreakable;

    // Item checks - specific
    public static boolean checkItemPotion;

    // Anti lag
    public static boolean limitLiquidSpreadEnabled, limitVehicleEnabled, limitOffhandSwapEnabled,
            limitWitherSpawnOnLagEnabled;
    public static int limitLiquidSpreadDisableTPS, limitVehicleDisableTPS, limitVehicleMinecartPerChunkLimit,
            limitWitherSpawnOnLagDisableTPS;
    public static String limitOffhandSwapMessage;

    // Misc / connection / nether
    public static boolean connectionMessageEnabled, connectionMessageUseDisplayName, connectionFirstJoinEnabled,
            connectionPreventKickEnabled, netherEnabled, netherTopBottomDoDamage;
    public static int netherTopLayer, netherBottomLayer;
    public static String connectionPlayerJoin, connectionPlayerLeave, connectionFirstJoinMessage, netherTopMessage,
            netherBottomMessage;
    public static List<String> connectionKickReasons;

    // Patch
    public static boolean preventBookBanEnabled, preventBuketPortalEnabled, perChunkLimitEnabled, preventDispenserCrash,
            gateWayPreventCrashExploit, gateWayPreventEntityEnterPortal, preventNBTBanEnabled, preventTeleportToBlock;
    public static int perChunkLimitTitleEntityMax, perChunkLimitSkullMax, preventNBTBanLimit;
    public static String preventBookBanMessage, preventBuketPortalMessage, perChunkLimitMessage, preventNBTBanMessage;

    public static void initConfig() {
        ConfigManager manager = Surf.configManager();

        // Prefix
        prefix = manager.getString("Prefix", "&6&l[&b&lSurf&6&l]&6 ", "Message prefix");

        // Anti Illegal
        antiIllegalCheckIllegalBlockEnabled = manager.getBoolean("anti-illegal.check-illegal-block.enabled", true, """
                Should remove illegal blocks when placed
                You can define illegal blocks in anti-illegal.checks.illegal-block-list""");
        antiIllegalCheckIllegalBlockMessage = manager.getString("anti-illegal.check-illegal-block.message", "&6You can not use this illegal block.");

        antiIllegalRemoveBlockEnchant = manager.getBoolean("anti-illegal.remove-block-enchants", true, """
                Whether remove all enchantments on blocks directly
                Disable it to check illegal enchants for block like other normal items.""");
        antiIllegalAllowInapplicableEnchant = manager.getBoolean("anti-illegal.allow-inapplicable-enchants-on-items", true, "Whether allow inapplicable enchants on items.");

        checkIllegalDamageEnabled = manager.getBoolean("check-illegal-damage.enabled", false);
        checkIllegalDamageMessage = manager.getString("check-illegal-damage.message", "&6You can not use this illegal item.");

        checkIllegalPotionEnabled = manager.getBoolean("check-illegal-potion.enabled", false);
        checkIllegalPotionMessage = manager.getString("check-illegal-potion.message", "&6You can not use this illegal potion.");

        stackedTotemRevertAsOneEnabled = manager.getBoolean("anti-illegal.revert-stacked-totem-as-one.enabled", false);

        antiIllegalDeleteIllegalsWhenFoundEnabled = manager.getBoolean("anti-illegal.delete-illegals-when-found.enabled", false, """
                Enable to delete illegals when found
                Disable to only clean illegal attributes""");

        antiIllegalCheckWhenPlayerJoinEnabled = manager.getBoolean("anti-illegal.check-when.PlayerJoin", false);
        antiIllegalCheckWhenHopperTransferEnabled = manager.getBoolean("anti-illegal.check-when.HopperTransfer", false);
        antiIllegalCheckWhenInventoryCloseEnabled = manager.getBoolean("anti-illegal.check-when.InventoryClose", false);
        antiIllegalCheckWhenInventoryOpenEnabled = manager.getBoolean("anti-illegal.check-when.InventoryOpen", false);
        antiIllegalCheckWhenItemPickupEnabled = manager.getBoolean("anti-illegal.check-when.ItemPickup", false);

        antiIllegalIllegalBlockList = manager.getList("anti-illegal.checks.illegal-block-list", ItemUtil.illegalBlocks);
        antiIllegalIllegalItemFlagList = manager.getList("anti-illegal.checks.illegal-item-flag-list", ItemUtil.illegalItemFlags, """
                Illegal item flags on itemstack for checking.
                In vanilla environment item would not has item flag.
                Note: delete this config section to let auto-regenerate if you change the server version.""");
        antiIllegalIllegalEnchantList = manager.getList("anti-illegal.checks.illegal-enchant-list", ItemUtil.illegalEnchants, """
                Set the value to -1 or remove the entire enchant from the list
                to disable check to that illegal enchant""");
        antiIllegalIllegalAttributeModifierList = manager.getList("anti-illegal.checks.illegal-attribute-modifier-list", ItemUtil.illegalAttributes, """
                Illegal attribute modifiers on itemstack for checking.
                In vanilla environment item would not has attribute modifier.
                Note: delete this config section to let auto-regenerate if you change the server version.""");

        // Item checks
        final String itemChecksPrefix = "item-checks.";
        checkItemAmount = manager.getBoolean(itemChecksPrefix + "amount", true);
        checkItemDurability = manager.getBoolean(itemChecksPrefix + "durability", true);
        checkItemEnchantments  = manager.getBoolean(itemChecksPrefix + "enchantments", true);
        checkItemAttributes = manager.getBoolean(itemChecksPrefix + "attributes", true);
        checkItemFlags =  manager.getBoolean(itemChecksPrefix + "item-flags", true);
        checkItemPotion = manager.getBoolean(itemChecksPrefix + "potion", true);
        checkItemUnbreakable =  manager.getBoolean(itemChecksPrefix + "unbreakable", false);

        // Anti Lag
        final String limitPrefix = "limit.";
        limitLiquidSpreadEnabled = manager.getBoolean(limitPrefix + limitPrefix + "liquid-spread.enabled", false, """
                water / lava flowing disable tps this is useful on new servers with lots of block physics updates that cause lag
                Set -1 to disable""");
        limitLiquidSpreadDisableTPS = manager.getInt(limitPrefix + "liquid-spread.disable-tps", 18);

        limitVehicleEnabled = manager.getBoolean(limitPrefix + "vehicle.enabled", false);
        limitVehicleDisableTPS = manager.getInt(limitPrefix + "vehicle.disable-tps", 18);

        limitVehicleMinecartPerChunkLimit = manager.getInt(limitPrefix + "vehicle.minecart-per-chunk", 500, "Amount of vehicles allowed per chunk");

        limitOffhandSwapEnabled = manager.getBoolean(limitPrefix + "offhand-swap.enabled", false);
        limitOffhandSwapMessage = manager.getString(limitPrefix + "offhand-swap.message", "&6You swap offhand too quick!");

        limitWitherSpawnOnLagEnabled = manager.getBoolean(limitPrefix + "wither-spawn.enabled", false);

        limitWitherSpawnOnLagDisableTPS = manager.getInt(limitPrefix + "wither-spawn.disable-tps", 18);

        // Misc / Connection / Nether
        final String miscPrefix = "misc.";
        final String connectionMsgPrefix = miscPrefix + "connection-message.";
        connectionMessageEnabled = manager.getBoolean(connectionMsgPrefix + "enabled", false, """
                These are the connection messages for when a player joins / leaves
                Use & for colours and %player% as a placeholder for the players name""");
        connectionMessageUseDisplayName = manager.getBoolean(connectionMsgPrefix + "use-display-name", true, """
                Whether use display name in connection messages.""");

        connectionPlayerJoin = manager.getString(connectionMsgPrefix + "player-join", "&7[&a+&7] &8%player%");
        connectionPlayerLeave = manager.getString(connectionMsgPrefix + "player-leave", "&7[&c-&7] &8%player%");

        connectionFirstJoinEnabled = manager.getBoolean(connectionMsgPrefix + "player-first-join.enabled", false);
        connectionFirstJoinMessage = manager.getString(connectionMsgPrefix + "player-first-join.message", "&c%player%&6 has joined the &bYour&3Server &6for the first time");

        connectionPreventKickEnabled = manager.getBoolean(miscPrefix + "connection-prevent-kick.enabled", true);

        connectionKickReasons = manager.getList(miscPrefix + "connection-prevent-kick.reasons", Arrays.asList(
                "Kicked for spamming",
                "Invalid hotbar selection (Hacking?)",
                "You released use item too quickly (Hacking?)",
                "You dropped your items too quickly (Hacking?)"
        ));

        final String netherPrefix = miscPrefix + "nether.";
        netherEnabled = manager.getBoolean(netherPrefix + "enabled", false, "Enable to prevent player go to Nether top or bottom layer");
        netherTopLayer = manager.getInt(netherPrefix + "top-layer", 127);
        netherBottomLayer = manager.getInt(netherPrefix + "bottom-layer", 0);
        netherTopMessage = manager.getString(netherPrefix + "top-message", "&6The nether top has been disabled due to lag");
        netherBottomMessage = manager.getString(netherPrefix + "bottom-message", "&6The nether bottom has been disabled due to lag");
        netherTopBottomDoDamage = manager.getBoolean(netherPrefix + "top-bottom-do-damage", false);

        // Patch
        final String patchPathPrefix = "patch.";
        preventBookBanEnabled = manager.getBoolean(patchPathPrefix + "prevent-book-ban.enabled", false);
        preventBookBanMessage = manager.getString(patchPathPrefix + "prevent-book-ban.message", "&6Detected a book ban, successfully cancelled.");

        preventBuketPortalEnabled = manager.getBoolean(patchPathPrefix + "prevent-buket-on-portal.enabled", false);
        preventBuketPortalMessage = manager.getString(patchPathPrefix + "prevent-buket-on-portal.message", "&6You can not do this");

        perChunkLimitEnabled = manager.getBoolean(patchPathPrefix + "per-chunk-limit.enabled", false, "ChunkBan skull limit tile entity limit and prevent message");
        perChunkLimitMessage = manager.getString(patchPathPrefix + "per-chunk-limit.message", "&6Detected a chunk ban, successfully cancelled.");
        perChunkLimitTitleEntityMax = manager.getInt(patchPathPrefix + "per-chunk-limit.tile-entity-max", 500);
        perChunkLimitSkullMax = manager.getInt(patchPathPrefix + "per-chunk-limit.skull-max", 100);

        preventDispenserCrash = manager.getBoolean(patchPathPrefix + "prevent-dispenser-crash.enabled", true);

        gateWayPreventCrashExploit = manager.getBoolean(patchPathPrefix + "gate-way.prevent-crash-exploit", false);
        gateWayPreventEntityEnterPortal = manager.getBoolean(patchPathPrefix + "gate-way.prevent-entity-enter-portal", false);

        preventNBTBanEnabled = manager.getBoolean(patchPathPrefix + "prevent-nbt-ban.enabled", true);
        preventNBTBanLimit = manager.getInt(patchPathPrefix + "prevent-nbt-ban.nbt-limit", 100000);
        preventNBTBanMessage = manager.getString(patchPathPrefix + "prevent-nbt-ban.message", "&6Detected a nbt ban, successfully cancelled.");

        preventTeleportToBlock = manager.getBoolean(patchPathPrefix + "prevent-teleport-to-block.enabled", true, """
                Prevent player uses ender pearl to teleport to inside of block,
                Enable this to let PVP more friendly.""");
    }
}
