package cn.dreeam.surf.config;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.modules.checks.ItemCheckRegistry;
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

    // Item checks
    public static boolean checkItemAmount;

    // Anti lag
    public static boolean limitLiquidSpreadEnabled, limitVehicleEnabled, limitOffhandSwapEnabled, limitWitherSpawnOnLagEnabled;
    public static int limitLiquidSpreadDisableTPS, limitVehicleDisableTPS, limitVehicleMinecartPerChunkLimit, limitWitherSpawnOnLagDisableTPS;
    public static String limitOffhandSwapMessage;

    // Misc / connection / nether
    public static boolean connectionMessageEnabled, connectionMessageUseDisplayName, connectionFirstJoinEnabled, connectionPreventKickEnabled, netherEnabled, netherTopBottomDoDamage;
    public static int netherTopLayer, netherBottomLayer;
    public static String connectionPlayerJoin, connectionPlayerLeave, connectionFirstJoinMessage, netherTopMessage, netherBottomMessage;
    public static List<String> connectionKickReasons;

    // Patch
    public static boolean preventBookBanEnabled, preventBuketPortalEnabled, perChunkLimitEnabled, preventDispenserCrash, gateWayPreventCrashExploit, gateWayPreventEntityEnterPortal, preventNBTBanEnabled, preventTeleportToBlock;
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

        /* New */
        checkItemAmount = manager.getBoolean("item-amount", true);

        // Anti Lag
        limitLiquidSpreadEnabled = manager.getBoolean("limit.liquid-spread.enabled", false, """
                water / lava flowing disable tps this is useful on new servers with lots of block physics updates that cause lag
                Set -1 to disable""");
        limitLiquidSpreadDisableTPS = manager.getInt("limit.liquid-spread.disable-tps", 18);

        limitVehicleEnabled = manager.getBoolean("limit.vehicle.enabled", false);
        limitVehicleDisableTPS = manager.getInt("limit.vehicle.disable-tps", 18);

        limitVehicleMinecartPerChunkLimit = manager.getInt("limit.vehicle.minecart-per-chunk", 500, "Amount of vehicles allowed per chunk");

        limitOffhandSwapEnabled = manager.getBoolean("limit.offhand-swap.enabled", false);
        limitOffhandSwapMessage = manager.getString("limit.offhand-swap.message", "&6You swap offhand too quick!");

        limitWitherSpawnOnLagEnabled = manager.getBoolean("limit.wither-spawn.enabled", false);

        limitWitherSpawnOnLagDisableTPS = manager.getInt("limit.wither-spawn.disable-tps", 18);

        // Misc / Connection / Nether
        connectionMessageEnabled = manager.getBoolean("connection-meesage.enabled", false, """
                These are the connection messages for when a player joins / leaves
                Use & for colours and %player% as a placeholder for the players name""");
        connectionMessageUseDisplayName = manager.getBoolean("connection-meesage.use-display-name", true, """
                Whether use display name in connection messages.""");

        connectionPlayerJoin = manager.getString("connection-meesage.player-join", "&7[&a+&7] &8%player%");
        connectionPlayerLeave = manager.getString("connection-meesage.player-leave", "&7[&c-&7] &8%player%");

        connectionFirstJoinEnabled = manager.getBoolean("connection-meesage.player-first-join.enabled", false);
        connectionFirstJoinMessage = manager.getString("connection-meesage.player-first-join.message", "&c%player%&6 has joined the &bYour&3Server &6for the first time");

        connectionPreventKickEnabled = manager.getBoolean("connection-prevent-kick.enabled", true);

        connectionKickReasons = manager.getList("connection-prevent-kick.reasons", Arrays.asList(
                "Kicked for spamming",
                "Invalid hotbar selection (Hacking?)",
                "You released use item too quickly (Hacking?)",
                "You dropped your items too quickly (Hacking?)"
        ));

        netherEnabled = manager.getBoolean("nether.enabled", false, "Enable to prevent player go to Nether top or bottom layer");
        netherTopLayer = manager.getInt("nether.top-layer", 127);
        netherBottomLayer = manager.getInt("nether.bottom-layer", 0);
        netherTopMessage = manager.getString("nether.top-message", "&6The nether top has been disabled due to lag");
        netherBottomMessage = manager.getString("nether.bottom-message", "&6The nether bottom has been disabled due to lag");
        netherTopBottomDoDamage = manager.getBoolean("nether.top-bottom-do-damage", false);

        // Patch
        preventBookBanEnabled = manager.getBoolean("prevent-book-ban.enabled", false);
        preventBookBanMessage = manager.getString("prevent-book-ban.message", "&6Detected a book ban, successfully cancelled.");

        preventBuketPortalEnabled = manager.getBoolean("prevent-buket-on-portal.enabled", false);
        preventBuketPortalMessage = manager.getString("prevent-buket-on-portal.message", "&6You can not do this");

        perChunkLimitEnabled = manager.getBoolean("per-chunk-limit.enabled", false, "ChunkBan skull limit tile entity limit and prevent message");
        perChunkLimitMessage = manager.getString("per-chunk-limit.message", "&6Detected a chunk ban, successfully cancelled.");
        perChunkLimitTitleEntityMax = manager.getInt("per-chunk-limit.tile-entity-max", 500);
        perChunkLimitSkullMax = manager.getInt("per-chunk-limit.skull-max", 100);

        preventDispenserCrash = manager.getBoolean("prevent-dispenser-crash.enabled", true);

        gateWayPreventCrashExploit = manager.getBoolean("gate-way.prevent-crash-exploit", false);
        gateWayPreventEntityEnterPortal = manager.getBoolean("gate-way.prevent-entity-enter-portal", false);

        preventNBTBanEnabled = manager.getBoolean("prevent-nbt-ban.enabled", true);
        preventNBTBanLimit = manager.getInt("prevent-nbt-ban.nbt-limit", 100000);
        preventNBTBanMessage = manager.getString("prevent-nbt-ban.message", "&6Detected a nbt ban, successfully cancelled.");

        preventTeleportToBlock = manager.getBoolean("prevent-teleport-to-block.enabled", true, """
                Prevent player uses ender pearl to teleport to inside of block,
                Enable this to let PVP more friendly.""");

        ItemCheckRegistry.loadChecks();
    }
}
