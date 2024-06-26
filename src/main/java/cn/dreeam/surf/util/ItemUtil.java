package cn.dreeam.surf.util;

import cn.dreeam.surf.config.Config;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemUtil {

    //public static final List<String> isBurrowBlock = Arrays.asList("ANVIL", "OBSIDIAN", "ENDER_CHEST");
    public static final List<String> illegalBlocks = initIllegalBlocks();
    public static final List<String> illegalItemFlags = initIllegalItemFlags();
    public static final List<String> illegalAttributes = initIllegalAttribute();
    public static final List<String> illegalEnchants = initIllegalEnchants();
    public static final Map<String, Integer> illegalEnchantsMap = initIllegalEnchantsMap();

    /*
    public static boolean isContainer(ItemStack i) {
        switch (i.getType()) {
            case
        }
        return ;
    }
     */

    public static boolean isAir(ItemStack i) {
        if (Util.majorVersion <= 13) {
            // From 1.14 org.bukkit.Material.isAir()
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

    public static boolean isIllegalBlock(ItemStack i) {
        return i.getType().isBlock() && Config.antiIllegalIllegalBlockList.contains(i.getType().toString());
    }

    public static boolean isEnchantedBlock(ItemStack i) {
        return i.getType().isBlock() && i.hasItemMeta() && i.getItemMeta().hasEnchants();
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
        int duration = Util.majorVersion >= 14 && effect.getType() == PotionEffectType.BAD_OMEN ? 120000 : 12000;

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
            for (String flag : Config.antiIllegalIllegalItemFlagList) {
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
                if (i.getItemMeta().getAttributeModifiers(Attribute.valueOf(attribute)) != null) {
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
        return isIllegalBlock(i) || isEnchantedBlock(i) || isIllegalPotion(i) || hasIllegalDurability(i)
                || isUnbreakable(i) || hasIllegalEnchants(i) || hasIllegalItemFlag(i) || hasIllegalAttributes(i);
    }

    public static void cleanIllegals(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();

        // if inventory is empty, skip
        if (contents.length == 0) return;

        for (ItemStack item : contents) {
            // if item is null, skip
            if (item == null) continue;

            ItemStack original = item.clone();
            ItemStack newItem = cleanIllegals(item);

            if (!original.equals(newItem)) {
                Util.println("&6Detected illegals " + original.getI18NDisplayName());
            }
        }
    }

    private static ItemStack cleanIllegals(ItemStack i) {
        if (i == null || isAir(i)) return new ItemStack(Material.AIR);
        if (isIllegalBlock(i) || isIllegalPotion(i)) return new ItemStack(Material.AIR);

        if (Config.antiIllegalDeleteIllegalsWhenFoundEnabled) {
            if (isIllegal(i)) {
                return new ItemStack(Material.AIR);
                //i.setAmount(0); // need to try??
            }
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
            enchants.keySet().forEach(i::removeEnchantment);
        } else {
            enchants.keySet().forEach(ench -> {
                if (ench.canEnchantItem(i)) {
                    String key = ench.getKey().getKey();
                    int level = enchants.get(ench);
                    if (illegalEnchantsMap.containsKey(key) && illegalEnchantsMap.get(key) > 0 && level > illegalEnchantsMap.get(key)) {
                        i.addEnchantment(ench, ench.getMaxLevel());
                    }
                } else {
                    // Should remove conflict/incompatible ench, since the item is illegal.
                    i.removeEnchantment(ench);
                }
            });
        }

        if (i.hasItemMeta()) {
            ItemMeta meta = i.getItemMeta();

            // Clear unbreakable flag
            meta.setUnbreakable(false);

            // Clean illegal itemFlag
            for (String flag : Config.antiIllegalIllegalItemFlagList) {
                if (meta.hasItemFlag(ItemFlag.valueOf(flag))) {
                    meta.removeItemFlags(ItemFlag.valueOf(flag));
                }
            }

            // Clean illegal AttributeModifier
            for (String attribute : Config.antiIllegalIllegalAttributeModifierList) {
                if (meta.getAttributeModifiers(Attribute.valueOf(attribute)) != null) {
                    meta.removeAttributeModifier(Attribute.valueOf(attribute));
                }
            }

            i.setItemMeta(meta); // Remember to set cleaned meta back
        }

        return i;
    }

    // TODO
    private static List<String> initIllegalBlocks() {
        List<String> list = new ArrayList<>(Arrays.asList(
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

        if (Util.minorVersion >= 12) {
        }

        return list;
    }

    private static List<String> initIllegalItemFlags() {
        List<String> list = new ArrayList<>();

        for (ItemFlag itemFlag : ItemFlag.values()) {
            list.add(itemFlag.toString());
        }

        return list;
    }

    private static List<String> initIllegalAttribute() {
        List<String> list = new ArrayList<>();

        for (Attribute attribute : Attribute.values()) {
            list.add(attribute.toString());
        }

        return list;
    }

    // TODO
    private static List<String> initIllegalEnchants() {
        return Arrays.asList(
                "protection:5",
                "fire_protection:5",
                "feather_falling:5",
                "blast_protection:5",
                "projectile_protection:5",
                "respiration:5",
                "aqua_affinity:5",
                "thorns:5",
                "depth_strider:5",
                "frost_walker:5",
                "binding_curse:5",
                "sharpness:5",
                "smite:5",
                "bane_of_arthropods:5",
                "knockback:5",
                "fire_aspect:5",
                "looting:5",
                "sweeping:5",
                "efficiency:5",
                "silk_touch:5",
                "unbreaking:5",
                "fortune:5",
                "power:5",
                "punch:5",
                "flame:5",
                "infinity:5",
                "luck_of_the_sea:5",
                "lure:5",
                "loyalty:5",
                "impaling:5",
                "riptide:5",
                "channeling:5",
                "multishot:5",
                "quick_charge:5",
                "piercing:5",
                "mending:5",
                "vanishing_curse:5",
                "soul_speed:5"
        );
    }

    private static Map<String, Integer> initIllegalEnchantsMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();

        for (String ench : illegalEnchants) {
            String[] list = ench.split(":");
            map.put(list[0], Integer.valueOf(list[1]));
        }

        return map;
    }
}