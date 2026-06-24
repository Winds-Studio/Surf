package cn.dreeam.surf.util.item;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.PlatformUtil;
import com.cryptomorin.xseries.XMaterial;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jspecify.annotations.Nullable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ItemUtil {

    private static final int COMMON_EFFECT_DURATION = 10 * 60 * 20; // Duration: 0:10:00
    private static final int HERO_OF_THE_VILLAGE_EFFECT_DURATION = 40 * 60 * 20; // Duration: 0:40:00
    private static final int BAD_OMEN_EFFECT_DURATION = (60 + 40) * 60 * 20; // Duration: 1:40:00
    private static final int RAID_OMEN_EFFECT_DURATION = 30 * 20; // Duration: 0:0:30
    private static final int TRIAL_OMEN_EFFECT_DURATION = (15 * 60 * 20) * (PlatformUtil.isNewerAndEqual(14, 0) ? 5 : 3); // Duration: 15mins * 3 or 5 (max Bad Omen amplifier)

    // For config-gen
    public static final List<String> defaultIllegalBlocks = initDefaultIllegalBlocks();
    public static final List<String> defaultIllegalItemFlags = initDefaultIllegalItemFlags();
    public static final List<String> defaultIllegalAttributes = initDefaultIllegalAttribute();
    public static final List<String> defaultMaxEnchantLevels = initDefaultMaxEnchantLevels();

    public static List<Material> illegalBlocks = new ArrayList<>();
    public static List<ItemFlag> illegalItemFlags = new ArrayList<>();
    public static List<Attribute> illegalAttributes = new ArrayList<>();
    public static Object2IntOpenHashMap<Enchantment> maxEnchantLevels = new Object2IntOpenHashMap<>();
    public static List<Material> checkRuleAmountWhitelistMaterials = new ArrayList<>();

    private static Method attributeValues;
    private static Method attributeValueOf;
    private static Method attributeName;

    /*
    public static boolean isContainer(ItemStack i) {
        switch (i.getType()) {
            case
        }
        return ;
    }
     */

    public static boolean isAir(ItemStack i) {
        if (PlatformUtil.isOlderAndEqual(13, 2)) {
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
        return i.getType() == XMaterial.WRITABLE_BOOK.get();
    }

    public static boolean isPotion(ItemStack i) {
        Material material = i.getType();
        return material == Material.POTION || material == Material.SPLASH_POTION || material == Material.LINGERING_POTION;
    }

    public static boolean isIllegalTotem(ItemStack i) {
        return i.getType().equals(XMaterial.TOTEM_OF_UNDYING.get()) && i.getAmount() > i.getMaxStackSize();
    }

    public static boolean isIllegalItem(ItemStack i) {
        return ItemUtil.illegalBlocks.contains(i.getType());
    }

    public static boolean isIllegalEffect(PotionEffect effect) {
        int duration;

        if (PlatformUtil.isNewerAndEqual(20, 5) && effect.getType() == PotionEffectType.TRIAL_OMEN) { // Trial Omen Effect >=1.20.5
            duration = TRIAL_OMEN_EFFECT_DURATION;
        } else if (PlatformUtil.isNewerAndEqual(20, 5) && effect.getType() == PotionEffectType.RAID_OMEN) { // Raid Omen Effect >=1.20.5
            duration = RAID_OMEN_EFFECT_DURATION;
        } else if (PlatformUtil.isNewerAndEqual(14, 0) && effect.getType() == PotionEffectType.BAD_OMEN) { // Bad Omen Effect >=1.14
            duration = BAD_OMEN_EFFECT_DURATION;
        } else if (PlatformUtil.isNewerAndEqual(14, 0) && effect.getType() == PotionEffectType.HERO_OF_THE_VILLAGE) { // Hero of the Village Effect >=1.14
            duration = HERO_OF_THE_VILLAGE_EFFECT_DURATION;
        } else {
            duration = COMMON_EFFECT_DURATION;
        }

        return effect.getAmplifier() > 5 || effect.getDuration() < 0 || effect.getDuration() > duration;
    }

    public static void initIllegalItemData() {
        initIllegalBlocks();
        initIllegalItemFlags();
        initIllegalAttributes();
        initMaxEnchantLevels();

        checkRuleAmountWhitelistMaterials.clear();

        for (String materialName : Config.checkRuleAmountWhitelist) {
            final Material material = Material.matchMaterial(materialName);
            if (material != null) {
                checkRuleAmountWhitelistMaterials.add(material);
            }
        }
    }

    // TODO - multi-version support
    private static List<String> initDefaultIllegalBlocks() {
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

        list.sort(String.CASE_INSENSITIVE_ORDER);

        return list;
    }

    private static List<String> initDefaultIllegalItemFlags() {
        final List<String> list = new ArrayList<>();

        for (ItemFlag flag : ItemFlag.values()) {
            list.add(flag.toString());
        }

        list.sort(String.CASE_INSENSITIVE_ORDER);

        return list;
    }

    private static List<String> initDefaultIllegalAttribute() {
        final List<String> list = new ArrayList<>();

        if (PlatformUtil.isNewerThan(21, 1)) {
            for (Attribute attribute : Registry.ATTRIBUTE) {
                final String name = attribute.key().value();

                list.add(name);
            }
        } else {
            Attribute[] attributes = getAttributeValues();

            if (attributes != null) {
                for (Attribute attribute : attributes) {
                    // For legacy versions, use the enum name,
                    // instead of enum key name in the config
                    final String rawName = getAttributeName(attribute);
                    if (rawName != null) {
                        list.add(rawName.toLowerCase(Locale.ROOT));
                    }
                }
            }
        }

        list.sort(String.CASE_INSENSITIVE_ORDER);

        return list;
    }

    private static List<String> initDefaultMaxEnchantLevels() {
        final List<String> list = new ArrayList<>();

        if (PlatformUtil.isNewerAndEqual(20, 6)) {
            final Registry<Enchantment> registryAccess = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
            for (Enchantment enchantment : registryAccess) {
                final String name = enchantment.key().value();
                final String maxLevel = String.valueOf(enchantment.getMaxLevel());

                list.add(name + ":" + maxLevel);
            }
        } else if (PlatformUtil.isNewerAndEqual(13, 0)) {
            for (Enchantment enchantment : Enchantment.values()) {
                final String name = enchantment.getKey().getKey();
                final String maxLevel = String.valueOf(enchantment.getMaxLevel());

                list.add(name + ":" + maxLevel);
            }
        } else {
            for (Enchantment enchantment : Enchantment.values()) {
                final String name = enchantment.getName();
                final String normalizedName = name.toLowerCase(Locale.ROOT);
                final String maxLevel = String.valueOf(enchantment.getMaxLevel());

                list.add(normalizedName + ":" + maxLevel);
            }
        }

        list.sort(String.CASE_INSENSITIVE_ORDER);

        return list;
    }

    private static void initIllegalBlocks() {
        illegalBlocks.clear();

        for (String materialName : defaultIllegalBlocks) {
            Material material = Material.matchMaterial(materialName);
            if (material == null) {
                Surf.LOGGER.warn("Invalid material name: {}", materialName);
                continue;
            }
            illegalBlocks.add(material);
        }
    }

    private static void initIllegalItemFlags() {
        illegalItemFlags.clear();

        for (String flagName : defaultIllegalItemFlags) {
            ItemFlag flag;

            try {
                flag = ItemFlag.valueOf(flagName);

                illegalItemFlags.add(flag);
            } catch (IllegalArgumentException e) {
                Surf.LOGGER.warn("Invalid item flag name: {}", flagName);
            }
        }
    }

    private static void initIllegalAttributes() {
        illegalAttributes.clear();

        for (String attributeName : defaultIllegalAttributes) {
            Attribute attribute;
            if (PlatformUtil.isNewerThan(21, 1)) {
                final NamespacedKey key = NamespacedKey.minecraft(attributeName);

                attribute = Registry.ATTRIBUTE.get(key);
            } else {
                attributeName = attributeName.toUpperCase(Locale.ROOT);

                attribute = getAttributeValueOf(attributeName);
            }

            if (attribute == null) {
                Surf.LOGGER.warn("Invalid attribute name: {}", attributeName);
                continue;
            }

            illegalAttributes.add(attribute);
        }
    }

    private static void initMaxEnchantLevels() {
        maxEnchantLevels.clear();

        for (String enchantmentData : defaultMaxEnchantLevels) {
            final String[] data = enchantmentData.split(":");
            final String enchantmentName = data[0];
            final String levelStr = data[1];

            final Enchantment enchantment = getEnchantmentName(enchantmentName);

            if (enchantment == null) {
                Surf.LOGGER.warn("Invalid enchantment name: {}", enchantmentName);
                continue;
            }

            final int maxLevel;

            try {
                maxLevel = Integer.parseInt(levelStr);
            } catch (NumberFormatException e) {
                Surf.LOGGER.warn("Invalid enchantment max level: {}", levelStr);
                continue;
            }

            maxEnchantLevels.put(enchantment, maxLevel);
        }
    }

    private static @Nullable Enchantment getEnchantmentName(String name) {
        if (PlatformUtil.isNewerThan(20, 6)) {
            final NamespacedKey key = NamespacedKey.minecraft(name.toLowerCase(Locale.ROOT));

            return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(key);
        } else if (PlatformUtil.isNewerAndEqual(13, 0)) {
            final NamespacedKey key = NamespacedKey.minecraft(name.toLowerCase(Locale.ROOT));

            return Enchantment.getByKey(key);
        } else {
            final String normalizedName = name.toUpperCase(Locale.ROOT);

            return Enchantment.getByName(normalizedName);
        }
    }

    // <= 1.21.1
    public static @Nullable Attribute getAttributeValueOf(String name) {
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

    // <= 1.21.1
    public static @Nullable String getAttributeName(Attribute attribute) {
        try {
            if (attributeName == null) {
                attributeName = Attribute.class.getMethod("name");
            }
            return (String) attributeName.invoke(attribute);
        } catch (ReflectiveOperationException e) {
            Surf.LOGGER.error(e);
            return null;
        }
    }

    // <= 1.21.1
    private static @Nullable Attribute[] getAttributeValues() {
        Attribute[] attributes = null;
        try {
            if (attributeValues == null) {
                attributeValues = Attribute.class.getMethod("values");
            }
            attributes = (Attribute[]) attributeValues.invoke(null);
        } catch (ReflectiveOperationException e) {
            Surf.LOGGER.error(e);
        }
        return attributes;
    }

    public static String getItemDisplayName(ItemStack i) {
        return i.getItemMeta().getDisplayName();
    }
}