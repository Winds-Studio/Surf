package cn.dreeam.surf.config;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.ConfHeader;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.List;

@ConfHeader({
        "Surf 5.0.0",
        "Contact me on QQ:2682173972 or Discord: dreeam___",
        "For help with this plugin"
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
    @ConfKey("anti-illegal.check-illegal-block.enabled")
    @ConfDefault.DefaultBoolean(true)
    boolean antiIllegalCheckIllegalBlockEnabled();

    @ConfKey("anti-illegal.check-illegal-block.message")
    @ConfDefault.DefaultString("%prefix%&6This block is not allowed")
    String antiIllegalCheckIllegalBlockMessage();

    @ConfKey("anti-illegal.checks.illegal-block-list")
    @ConfDefault.DefaultStrings({
            "BARRIER",
            "BEDROCK",
            "REPEATING_COMMAND_BLOCK",
            "COMMAND_BLOCK_MINECART",
            "CHAIN_COMMAND_BLOCK",
            "COMMAND_BLOCK",
            "KNOWLEDGE_BOOK",
            "SPAWNER",
            "END_PORTAL",
            "END_PORTAL_FRAME",
            "END_GATEWAY",
            "NETHER_PORTAL",
            "STRUCTURE_BLOCK",
            "STRUCTURE_VOID",
            "JIGSAW",
            "LIGHT",
            "REINFORCED_DEEPSLATE"
    })
    List<String> antiIllegalIllegalBlockList();

    @ConfKey("anti-illegal.checks.illegal-item-flag-list")
    @ConfDefault.DefaultStrings({
            "HIDE_ARMOR_TRIM",
            "HIDE_ATTRIBUTES",
            "HIDE_DESTROYS",
            "HIDE_DYE",
            "HIDE_ENCHANTS",
            "HIDE_PLACED_ON",
            "HIDE_POTION_EFFECTS",
            "HIDE_UNBREAKABLE"
    })
    List<String> antiIllegalIllegalItemFlagList();

    @ConfComments({
            "Set the value to -1 or remove the entire enchant",
            "to disable check to that illegal enchant"
    })
    @ConfKey("anti-illegal.checks.illegal-enchant-list")
    @ConfDefault.DefaultStrings({
            "ARROW_DAMAGE:5",
            "ARROW_FIRE:5",
            "ARROW_INFINITE:5",
            "ARROW_KNOCKBACK:5",
            "BINDING_CURSE:5",
            "CHANNELING:5",
            "DAMAGE_ALL:5",
            "DAMAGE_ARTHROPODS:5",
            "DAMAGE_UNDEAD:5",
            "DEPTH_STRIDER:5",
            "DIG_SPEED:5",
            "DURABILITY:5",
            "FIRE_ASPECT:5",
            "FROST_WALKER:5",
            "IMPALING:5",
            "KNOCKBACK:5",
            "LOOT_BONUS_BLOCKS:5",
            "LOOT_BONUS_MOBS:5",
            "LOYALTY:5",
            "LUCK:5",
            "LURE:5",
            "MENDING:5",
            "MULTISHOT:5",
            "OXYGEN:5",
            "PIERCING:5",
            "PROTECTION_ENVIRONMENTAL:5",
            "PROTECTION_EXPLOSIONS:5",
            "PROTECTION_FALL:5",
            "PROTECTION_FIRE:5",
            "PROTECTION_PROJECTILE:5",
            "QUICK_CHARGE:5",
            "RIPTIDE:5",
            "SILK_TOUCH:5",
            "SOUL_SPEED:5",
            "SWEEPING_EDGE:5",
            "SWIFT_SNEAK:5",
            "THORNS:5",
            "VANISHING_CURSE:5",
            "WATER_WORKER:5"
    })
    List<String> antiIllegalIllegalEnchantList();

    @ConfKey("anti-illegal.checks.illegal-attribute-modifier-list")
    @ConfDefault.DefaultStrings({
            "GENERIC_ARMOR",
            "GENERIC_ARMOR_TOUGHNESS",
            "GENERIC_ATTACK_DAMAGE",
            "GENERIC_ATTACK_KNOCKBACK",
            "GENERIC_ATTACK_SPEED",
            "GENERIC_FLYING_SPEED",
            "GENERIC_FOLLOW_RANGE",
            "GENERIC_KNOCKBACK_RESISTANCE",
            "GENERIC_LUCK",
            "GENERIC_MAX_ABSORPTION",
            "GENERIC_MAX_HEALTH",
            "GENERIC_MOVEMENT_SPEED",
            "HORSE_JUMP_STRENGTH",
            "ZOMBIE_SPAWN_REINFORCEMENTS",
    })
    List<String> antiIllegalIllegalAttributeModifierList();

    @ConfKey("anti-illegal.check-when.HopperTransfer")
    @ConfDefault.DefaultBoolean(false)
    boolean antiIllegalCheckWhenHopperTransferEnabled();

    @ConfKey("anti-illegal.check-when.InventoryClose")
    @ConfDefault.DefaultBoolean(false)
    boolean antiIllegalCheckWhenInventoryCloseEnabled();

    @ConfKey("anti-illegal.check-when.InventoryOpen")
    @ConfDefault.DefaultBoolean(false)
    boolean antiIllegalCheckWhenInventoryOpenEnabled();

    @ConfKey("anti-illegal.check-when.ItemPickup")
    @ConfDefault.DefaultBoolean(false)
    boolean antiIllegalCheckWhenItemPickupEnabled();

    @ConfKey("check-illegal-damage.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean checkIllegalDamageEnabled();

    @ConfKey("check-illegal-damage.message")
    @ConfDefault.DefaultString("%prefix%&6You can not use this illegal item.")
    String checkIllegalDamageMessage();

    @ConfKey("check-illegal-potion.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean checkIllegalPotionEnabled();

    @ConfKey("check-illegal-potion.message")
    @ConfDefault.DefaultString("%prefix%&6You can not use this illegal potion")
    String checkIllegalPotionMessage();

    @ConfKey("anti-illegal.revert-stacked-totem-as-one.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean stackedTotemRevertAsOneEnabled();

    // Anti Lag
    @ConfComments({
            "water / lava flowing disable tps this is useful on new servers with lots of block physics updates that cause lag",
            "Set -1 to disable"
    })
    @ConfKey("limit.liquid-spread.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean limitLiquidSpreadEnabled();

    @ConfKey("limit.liquid-spread.disable-tps")
    @ConfDefault.DefaultInteger(18)
    int limitLiquidSpreadDisableTPS();

    @ConfKey("limit.vehicle.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean limitVehicleEnabled();

    @ConfKey("limit.vehicle.disable-tps")
    @ConfDefault.DefaultInteger(18)
    int limitVehicleDisableTPS();

    @ConfComments({
            "Amount of vehicles allowed per chunk"
    })
    @ConfKey("limit.vehicle.minecart-per-chunk")
    @ConfDefault.DefaultInteger(500)
    int limitVehicleMinecartPerChunkLimit();

    @ConfKey("limit.offhand-swap.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean limitOffhandSwapEnabled();

    @ConfKey("limit.offhand-swap.message")
    @ConfDefault.DefaultString("%prefix%&6You can not do this.")
    String limitOffhandSwapMessage();

    @ConfKey("limit.wither-spawn.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean limitWitherSpawnOnLagEnabled();

    @ConfKey("limit.wither-spawn.disable-tps")
    @ConfDefault.DefaultInteger(18)
    int limitWitherSpawnOnLagDisableTPS();

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
    @ConfDefault.DefaultString("%prefix%&6The nether top has been disabled due to lag")
    String netherTopMessage();

    @ConfKey("nether.bottom-message")
    @ConfDefault.DefaultString("%prefix%&6The nether bottom has been disabled due to lag")
    String netherBottomMessage();

    @ConfKey("nether.top-bottom-do-damage")
    @ConfDefault.DefaultBoolean(false)
    boolean netherTopBottomDoDamage();

    // Patch
    @ConfKey("prevent-book-ban.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean preventBookBanEnabled();

    @ConfKey("prevent-book-ban.message")
    @ConfDefault.DefaultString("%prefix%&6You have been unbookbanned")
    String preventBookBanMessage();

    @ConfKey("prevent-buket-on-portal.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean preventBuketPortalEnabled();

    @ConfKey("prevent-buket-on-portal.message")
    @ConfDefault.DefaultString("%prefix%&6You can not do this")
    String preventBuketPortalMessage();

    @ConfComments({
            "ChunkBan skull limit tile entity limit and prevent message"
    })
    @ConfKey("per-chunk-limit.enabled")
    @ConfDefault.DefaultBoolean(false)
    boolean perChunkLimitEnabled();

    @ConfKey("per-chunk-limit.message")
    @ConfDefault.DefaultString("%prefix%&6ChunkBan has been disabled")
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
    @ConfDefault.DefaultString("%prefix%&6You have been un-NBT-banned")
    String preventNBTBanMessage();
}

