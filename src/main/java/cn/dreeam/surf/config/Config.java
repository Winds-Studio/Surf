package cn.dreeam.surf.config;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.item.ItemUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    // Anti illegal
    public static boolean antiIllegalCheckIllegalBlockEnabled, antiIllegalRemoveBlockEnchant, antiIllegalAllowInapplicableEnchant, checkIllegalDamageEnabled, checkIllegalPotionEnabled, stackedTotemRevertAsOneEnabled, antiIllegalDeleteIllegalsWhenFoundEnabled;
    public static String antiIllegalCheckIllegalBlockMessage, checkIllegalDamageMessage, checkIllegalPotionMessage;

    // Item checks - triggers
    public static boolean checkTriggerOnJoin, checkTriggerOnPickup, checkTriggerOnInvOpen, checkTriggerOnInvClose,
            checkTriggerOnHopperTransfer;

    // Item checks - general rules
    public static boolean checkRuleAmount, checkRuleDurability, checkRuleEnchantments, checkRuleAttributes,
            checkRuleFlags, checkRuleUnbreakable;

    // Item checks - definitions
    public static List<String> checkRuleAmountWhitelist;

    // Item checks - specific rules
    public static boolean checkRulePotion, checkRuleRemoveLegacyEnchantedGoldenApple;

    // Item checks - definitions
    public static List<String> checkDefinitionIllegalBlocks, checkDefinitionIllegalItemFlags, checkDefinitionIllegalAttributes, checkDefinitionMaxEnchantLevels;

    // Anti lag
    public static boolean limitLiquidSpreadEnabled, limitVehicleEnabled, limitOffhandSwapEnabled,
            limitWitherSpawnOnLagEnabled;
    public static int limitLiquidSpreadDisableTPS, limitVehicleDisableTPS, limitVehicleMinecartPerChunkLimit,
            limitWitherSpawnOnLagDisableTPS;
    public static String limitOffhandSwapMessage;

    // Patch
    public static boolean preventBookBanEnabled, preventBuketPortalEnabled, perChunkLimitEnabled, preventDispenserCrash,
            gateWayPreventCrashExploit, gateWayPreventEntityEnterPortal, preventNBTBanEnabled, preventTeleportToBlock;
    public static int perChunkLimitTitleEntityMax, perChunkLimitSkullMax, preventNBTBanLimit;
    public static String preventBookBanMessage, preventBuketPortalMessage, perChunkLimitMessage, preventNBTBanMessage;

    // Misc / connection / nether
    public static String prefix;
    public static boolean connectionMessageEnabled, connectionMessageUseDisplayName, connectionFirstJoinEnabled,
            connectionPreventKickEnabled, netherEnabled, netherTopBottomDoDamage;
    public static int netherTopLayer, netherBottomLayer;
    public static String connectionPlayerJoin, connectionPlayerLeave, connectionFirstJoinMessage, netherTopMessage,
            netherBottomMessage;
    public static List<String> connectionKickReasons;

    public static void initConfig() {
        ConfigManager manager = Surf.configManager();

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

        // Item checks
        final String checkPrefix = "item-checks.";
        final String checkTriggerPrefix = checkPrefix + "triggers.";
        final String checkGeneralRulePrefix = checkPrefix + "rules.general.";
        final String checkSpecificRulePrefix = checkPrefix + "rules.specific.";
        final String checkDefinitionPrefix = checkPrefix + "definitions.";

        // Item checks - triggers
        checkTriggerOnJoin = manager.getBoolean(checkTriggerPrefix + "on-player-join", false);
        checkTriggerOnPickup = manager.getBoolean(checkTriggerPrefix + "on-item-pickup", false);
        checkTriggerOnInvOpen = manager.getBoolean(checkTriggerPrefix + "on-inventory-open", false);
        checkTriggerOnInvClose = manager.getBoolean(checkTriggerPrefix + "on-inventory-close", false);
        checkTriggerOnHopperTransfer = manager.getBoolean(checkTriggerPrefix + "on-hopper-transfer", false);

        // Item checks - general rules
        checkRuleAmount = manager.getBoolean(checkGeneralRulePrefix + "amount.enabled", true);
        checkRuleAmountWhitelist = manager.getList(checkGeneralRulePrefix + "amount.witelist", new ArrayList<>());
        checkRuleDurability = manager.getBoolean(checkGeneralRulePrefix + "durability.enabled", true);
        checkRuleEnchantments  = manager.getBoolean(checkGeneralRulePrefix + "enchantments.enabled", true);
        checkRuleAttributes = manager.getBoolean(checkGeneralRulePrefix + "attributes.enabled", true);
        checkRuleFlags =  manager.getBoolean(checkGeneralRulePrefix + "item-flags.enabled", true);
        checkRuleUnbreakable =  manager.getBoolean(checkGeneralRulePrefix + "unbreakable.enabled", false);

        // Item checks - specific rules
        checkRulePotion = manager.getBoolean(checkSpecificRulePrefix + "potion.enabled", true);
        checkRuleRemoveLegacyEnchantedGoldenApple = manager.getBoolean(checkSpecificRulePrefix + "remove-legacy-enchanted-golden-apple.enabled", false);

        // Item checks - definitions
        checkDefinitionIllegalBlocks = manager.getList(checkDefinitionPrefix + "illegal-blocks", ItemUtil.defaultIllegalBlocks);
        checkDefinitionIllegalItemFlags = manager.getList(checkDefinitionPrefix + "illegal-item-flags", ItemUtil.defaultIllegalItemFlags, """
                Illegal item flags for item checking.
                In vanilla environment item would not has item flag.
                Note: delete this config section to let auto-regenerate if you change the server version.""");
        checkDefinitionIllegalAttributes = manager.getList(checkDefinitionPrefix + "illegal-attribute-modifiers", ItemUtil.defaultIllegalAttributes, """
                Illegal attribute modifiers for item checking.
                In vanilla environment item would not has attribute modifier.
                Note: delete this config section to let auto-regenerate if you change the server version.""");
        checkDefinitionMaxEnchantLevels = manager.getList(checkDefinitionPrefix + "max-enchantment-levels", ItemUtil.defaultMaxEnchantLevels, """
                Max allowed enchantment levels for item checking.
                Set the value to -1 or remove the entire enchant from the list
                to disable check to that illegal enchant""");

        ItemUtil.initIllegalItemData();

        // Anti Lag
        final String limitPrefix = "limit.";
        limitLiquidSpreadEnabled = manager.getBoolean(limitPrefix + "liquid-spread.enabled", false, """
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

        // Misc / Connection / Nether
        final String miscPrefix = "misc.";
        final String connectionMsgPrefix = miscPrefix + "connection-message.";

        prefix = manager.getString(miscPrefix + "prefix", "&6&l[&b&lSurf&6&l]&6 ", "Message prefix");
        connectionPreventKickEnabled = manager.getBoolean(miscPrefix + "connection-prevent-kick.enabled", true);

        connectionKickReasons = manager.getList(miscPrefix + "connection-prevent-kick.reasons", Arrays.asList(
                "Kicked for spamming",
                "Invalid hotbar selection (Hacking?)",
                "You released use item too quickly (Hacking?)",
                "You dropped your items too quickly (Hacking?)"
        ));

        connectionMessageEnabled = manager.getBoolean(connectionMsgPrefix + "enabled", false, """
                These are the connection messages for when a player joins / leaves
                Use & for colours and %player% as a placeholder for the players name""");
        connectionMessageUseDisplayName = manager.getBoolean(connectionMsgPrefix + "use-display-name", true, """
                Whether use display name in connection messages.""");

        connectionPlayerJoin = manager.getString(connectionMsgPrefix + "player-join", "&7[&a+&7] &8%player%");
        connectionPlayerLeave = manager.getString(connectionMsgPrefix + "player-leave", "&7[&c-&7] &8%player%");

        connectionFirstJoinEnabled = manager.getBoolean(connectionMsgPrefix + "player-first-join.enabled", false);
        connectionFirstJoinMessage = manager.getString(connectionMsgPrefix + "player-first-join.message", "&c%player%&6 has joined the &bYour&3Server &6for the first time");

        final String netherPrefix = miscPrefix + "nether.";
        netherEnabled = manager.getBoolean(netherPrefix + "enabled", false, "Enable to prevent player go to Nether top or bottom layer");
        netherTopLayer = manager.getInt(netherPrefix + "top-layer", 127);
        netherBottomLayer = manager.getInt(netherPrefix + "bottom-layer", 0);
        netherTopMessage = manager.getString(netherPrefix + "top-message", "&6The nether top has been disabled due to lag");
        netherBottomMessage = manager.getString(netherPrefix + "bottom-message", "&6The nether bottom has been disabled due to lag");
        netherTopBottomDoDamage = manager.getBoolean(netherPrefix + "top-bottom-do-damage", false);
    }
}
