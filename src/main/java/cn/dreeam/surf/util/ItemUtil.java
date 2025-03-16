package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.config.Config;
import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ItemUtil {

    private static final int COMMON_EFFECT_DURATION = 10 * 60 * 20; // Duration: 0:10:00
    private static final int HERO_OF_THE_VILLAGE_EFFECT_DURATION = 40 * 60 * 20; // Duration: 0:40:00
    private static final int BAD_OMEN_EFFECT_DURATION = (60 + 40) * 60 * 20; // Duration: 1:40:00
    private static final int RAID_OMEN_EFFECT_DURATION = 30 * 20; // Duration: 0:0:30
    private static final int TRIAL_OMEN_EFFECT_DURATION = (15 * 60 * 20) * (Util.isNewerAndEqual(14, 0) ? 5 : 3); // Duration: 15mins * 3 or 5 (max Bad Omen amplifier)

    public static final List<String> illegalBlocks = initIllegalBlocks();
    public static final List<String> illegalItemFlags = initIllegalItemFlags();
    public static final List<String> illegalAttributes = initIllegalAttribute();
    public static final List<String> illegalEnchants = initIllegalEnchants();
    public static final Map<String, Integer> illegalEnchantsMap = initIllegalEnchantsMap();

    private static Method attributeValueOf;
    private static Method attributeValues;

    /*
    public static boolean isContainer(ItemStack i) {
        switch (i.getType()) {
            case
        }
        return ;
    }
     */

    public static boolean isAir(ItemStack i) {
        if (Util.isOlderAndEqual(13, 2)) {
            // From >=1.14 org.bukkit.Material.isAir()
            switch (i.getType()) {
                //<editor-fold defaultstate="collapsed" desc="isAir">
                case AIR:
                case CAVE_AIR:
                case VOID_AIR:
                    // ----- Legacy Separator -----
                case LEGACY_AIR:
                    //</editor-fold>
                    return true;
                default:
                    return false;
            }
        }

        return i.getType().isAir();
    }

    public static boolean isWritableBook(ItemStack i) {
        return i.getType() == XMaterial.WRITABLE_BOOK.parseMaterial();
    }

    public static boolean isIllegalTotem(ItemStack i) {
        return i.getType().equals(XMaterial.TOTEM_OF_UNDYING.parseMaterial()) && i.getAmount() > i.getMaxStackSize();
    }

    public static boolean isIllegalItem(ItemStack i) {
        return Config.antiIllegalIllegalBlockList.contains(i.getType().toString());
    }

    public static boolean isEnchantedBlock(ItemStack i) {
        return i.getType().isBlock() && Config.antiIllegalRemoveBlockEnchant && i.hasItemMeta() && i.getItemMeta().hasEnchants();
    }

    public static boolean isIllegalPotion(ItemStack i) {
        if (i.getType().toString().contains("POTION") && i.hasItemMeta()) {
            PotionMeta pot = (PotionMeta) i.getItemMeta();

            for (PotionEffect effect : pot.getCustomEffects()) {
                if (isIllegalEffect(effect)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isUnbreakable(ItemStack i) {
        return i.hasItemMeta() && i.getItemMeta().isUnbreakable();
    }

    public static boolean isIllegalEffect(PotionEffect effect) {
        int duration;

        if (Util.isNewerAndEqual(20, 5) && effect.getType() == PotionEffectType.TRIAL_OMEN) { // Trial Omen Effect >=1.20.5
            duration = TRIAL_OMEN_EFFECT_DURATION;
        } else if (Util.isNewerAndEqual(20, 5) && effect.getType() == PotionEffectType.RAID_OMEN) { // Raid Omen Effect >=1.20.5
            duration = RAID_OMEN_EFFECT_DURATION;
        } else if (Util.isNewerAndEqual(14, 0) && effect.getType() == PotionEffectType.BAD_OMEN) { // Bad Omen Effect >=1.14
            duration = BAD_OMEN_EFFECT_DURATION;
        } else if (Util.isNewerAndEqual(14, 0) && effect.getType() == PotionEffectType.HERO_OF_THE_VILLAGE) { // Hero of the Village Effect >=1.14
            duration = HERO_OF_THE_VILLAGE_EFFECT_DURATION;
        } else {
            duration = COMMON_EFFECT_DURATION;
        }

        return effect.getAmplifier() > 5 || effect.getDuration() < 0 || effect.getDuration() > duration;
    }

    public static boolean hasIllegalDurability(ItemStack i) {
        return i.getDurability() > i.getType().getMaxDurability() || i.getDurability() < 0;
    }

    public static boolean hasIllegalEnchants(ItemStack i) {
        Map<Enchantment, Integer> enchants = i.getEnchantments();
        for (Enchantment ench : enchants.keySet()) {
            String key = ench.getKey().getKey();
            int level = enchants.get(ench);
            if (illegalEnchantsMap.containsKey(key)
                    && illegalEnchantsMap.get(key) > 0
                    && level > illegalEnchantsMap.get(key)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasIllegalItemFlag(ItemStack i) {
        if (i.hasItemMeta()) {
            final boolean isBanner = i.getType().toString().contains("BANNER");

            for (String flag : Config.antiIllegalIllegalItemFlagList) {
                if (isBanner && flag.equalsIgnoreCase("HIDE_ADDITIONAL_TOOLTIP")) {
                    continue;
                }

                if (i.getItemMeta().hasItemFlag(ItemFlag.valueOf(flag))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasIllegalAttributes(ItemStack i) {
        if (i.hasItemMeta()) {
            for (String attribute : Config.antiIllegalIllegalAttributeModifierList) {
                if (i.getItemMeta().getAttributeModifiers(getAttributeByName(attribute)) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    // TODO
    public static boolean hasIllegalTag(ItemStack i) {
        return false;
    }

    public static boolean isIllegal(ItemStack i) {
        return isIllegalItem(i) || isEnchantedBlock(i) || isIllegalPotion(i) || hasIllegalDurability(i)
                || isUnbreakable(i) || hasIllegalEnchants(i) || hasIllegalItemFlag(i) || hasIllegalAttributes(i);
    }

    public static void cleanIllegals(Inventory inventory, String name) {
        ItemStack[] contents = inventory.getContents();

        // if inventory is empty, skip
        if (contents.length == 0) return;

        for (ItemStack item : contents) {
            // if item is null, skip
            if (item == null) continue;

            ItemStack original = item.clone();
            ItemStack newItem = cleanIllegals(item);

            if (!original.equals(newItem)) {
                if (newItem.equals(ItemStack.empty())) {
                    item.setAmount(0);
                }

                MessageUtil.println(String.format(
                        "&6Detected illegals %s on %s",
                        ItemUtil.getItemDisplayName(original),
                        name
                ) );
            }
        }
    }

    private static ItemStack cleanIllegals(ItemStack i) {
        if (i == null || isAir(i)) return ItemStack.empty();
        if (isIllegalItem(i) || isIllegalPotion(i)) return ItemStack.empty();

        if (Config.antiIllegalDeleteIllegalsWhenFoundEnabled && isIllegal(i)) {
            return ItemStack.empty();
        }

        // Clean oversize durability
        if (i.getDurability() > i.getType().getMaxDurability()) {
            i.setDurability(i.getType().getMaxDurability());
        }

        // Clean negative durability
        if (i.getDurability() < 0) {
            i.setDurability((short) 1);
        }

        // Clean illegal Enchantment
        Map<Enchantment, Integer> enchants = i.getEnchantments();
        if (i.getType().isBlock()) {
            if (Config.antiIllegalRemoveBlockEnchant) {
                // Directly remove enchs
                enchants.keySet().forEach(i::removeEnchantment);
            } else {
                // Correct enchs
                enchants.keySet().forEach(ench -> {
                    String key = ench.getKey().getKey();
                    int level = enchants.get(ench);
                    if (illegalEnchantsMap.containsKey(key) && illegalEnchantsMap.get(key) > 0 && level > illegalEnchantsMap.get(key)) {
                        i.addUnsafeEnchantment(ench, ench.getMaxLevel());
                    }
                });
            }
        } else {
            enchants.keySet().forEach(ench -> {
                if (Config.antiIllegalAllowInapplicableEnchant || ench.canEnchantItem(i)) {
                    String key = ench.getKey().getKey();
                    int level = enchants.get(ench);
                    if (illegalEnchantsMap.containsKey(key) && illegalEnchantsMap.get(key) > 0 && level > illegalEnchantsMap.get(key)) {
                        i.addUnsafeEnchantment(ench, ench.getMaxLevel());
                    }
                } else {
                    // Should remove conflict/incompatible ench, since the item is illegal.
                    i.removeEnchantment(ench);
                }
            });
        }

        if (i.hasItemMeta()) {
            ItemMeta meta = i.getItemMeta();
            final boolean isBanner = i.getType().toString().contains("BANNER");

            // Clear unbreakable flag
            meta.setUnbreakable(false);

            // Clean illegal itemFlag
            for (String flag : Config.antiIllegalIllegalItemFlagList) {
                if (isBanner && flag.equalsIgnoreCase("HIDE_ADDITIONAL_TOOLTIP")) {
                    continue;
                }

                if (meta.hasItemFlag(ItemFlag.valueOf(flag))) {
                    meta.removeItemFlags(ItemFlag.valueOf(flag));
                }
            }

            // Clean illegal AttributeModifier
            for (String attributeStr : Config.antiIllegalIllegalAttributeModifierList) {
                Attribute attribute = getAttributeByName(attributeStr);

                if (attribute != null && meta.getAttributeModifiers(attribute) != null) {
                    meta.removeAttributeModifier(attribute);
                }
            }

            i.setItemMeta(meta); // Remember to set cleaned meta back
        }

        return i;
    }

    // TODO
    private static List<String> initIllegalBlocks() {
        final List<String> list = new ArrayList<>(Arrays.asList(
                "BARRIER",
                "BEDROCK",
                "COMMAND_BLOCK",
                "REPEATING_COMMAND_BLOCK",
                "CHAIN_COMMAND_BLOCK",
                "COMMAND_BLOCK_MINECART",
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
        ));

        return list;
    }

    private static List<String> initIllegalItemFlags() {
        final List<String> list = new ArrayList<>();

        for (ItemFlag itemFlag : ItemFlag.values()) {
            final String itemFlagStr = itemFlag.toString();

            list.add(itemFlagStr);
        }

        return list;
    }

    private static List<String> initIllegalAttribute() {
        final List<String> list = new ArrayList<>();

        for (Object attribute : getAttributes()) {
            final String attributeStr = attribute.toString().toLowerCase(Locale.ROOT);

            list.add(attributeStr);
        }

        return list;
    }

    // TODO - multi version compatibility
    private static List<String> initIllegalEnchants() {
        return Arrays.asList(
                "protection:4",
                "fire_protection:4",
                "feather_falling:4",
                "blast_protection:4",
                "projectile_protection:4",
                "respiration:3",
                "aqua_affinity:1",
                "thorns:3",
                "depth_strider:3",
                "frost_walker:2",
                "binding_curse:1",
                "sharpness:5",
                "smite:5",
                "bane_of_arthropods:5",
                "knockback:2",
                "fire_aspect:2",
                "looting:3",
                "efficiency:5",
                "silk_touch:1",
                "unbreaking:3",
                "fortune:3",
                "power:5",
                "punch:2",
                "flame:1",
                "infinity:1",
                "luck_of_the_sea:3",
                "lure:3",
                "loyalty:3",
                "impaling:5",
                "riptide:3",
                "channeling:1",
                "multishot:1",
                "quick_charge:3",
                "piercing:4",
                "mending:1",
                "vanishing_curse:1",
                "soul_speed:3",
                "sweeping_edge:3",
                "swift_sneak:3",
                "density:5",
                "breach:4",
                "wind_burst:3"
        );
    }

    private static Map<String, Integer> initIllegalEnchantsMap() {
        final Map<String, Integer> map = new HashMap<>();

        for (String ench : illegalEnchants) {
            final String[] list = ench.split(":");

            map.put(list[0], Integer.valueOf(list[1]));
        }

        return map;
    }

    public static String getItemDisplayName(ItemStack itemStack) {
        return PlainTextComponentSerializer.plainText().serialize(itemStack.displayName());
    }

    private static Attribute getAttributeByName(String name) {
        if (Util.isNewerThan(21, 1)) {
            return Registry.ATTRIBUTE.get(Key.key(name));
        }

        try {
            if (attributeValueOf == null) {
                attributeValueOf = Attribute.class.getMethod("valueOf", String.class);
            }

            return (Attribute) attributeValueOf.invoke(null, name);
        } catch (ReflectiveOperationException e) {
            Surf.LOGGER.error(e);
            return null;
        }
    }

    private static Object[] getAttributes() {
        if (Util.isNewerThan(21, 1)) {
            return Registry.ATTRIBUTE.stream().map(Keyed::getKey).toArray(Key[]::new);
        }

        try {
            if (attributeValues == null) {
                attributeValues = Attribute.class.getMethod("values");
            }

            return (Object[]) attributeValues.invoke(null);
        } catch (ReflectiveOperationException e) {
            Surf.LOGGER.error(e);
            return null;
        }
    }
}