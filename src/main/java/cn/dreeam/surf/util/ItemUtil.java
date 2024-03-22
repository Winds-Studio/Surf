package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemUtil {

    public static List<String> isBurrowBlock = Arrays.asList("ANVIL", "OBSIDIAN", "ENDER_CHEST");
    public static final Map<String, Integer> illegalEnchants = initIllegalEnchantMap();

    /*
    public static boolean isContainer(ItemStack i) {
        switch (i.getType()) {
            case
        }
        return ;
    }
     */

    public static boolean isAir(ItemStack i) {
        if (Surf.majorVersion <= 13) {
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

    public static boolean isBook(ItemStack i) {
        return i.getType().toString().endsWith("BOOK") || i.getType().toString().equals("LEGACY_BOOK_AND_QUILL");
    }

    public static boolean isIllegalTotem(ItemStack i) {
        return i.getType().equals(XMaterial.TOTEM_OF_UNDYING.parseMaterial()) && i.getAmount() > i.getMaxStackSize();
    }

    public static boolean isIllegalBlock(ItemStack i) {
        return i.getType().isBlock() && Surf.config.antiIllegalIllegalBlockList().contains(i.getType().toString());
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
        int duration = effect.getType() == PotionEffectType.BAD_OMEN ? 120000 : 12000;

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
            if (illegalEnchants.containsKey(key)
                    && illegalEnchants.get(key) > 0
                    && level > illegalEnchants.get(key)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasIllegalItemFlag(ItemStack i) {
        if (i.hasItemMeta()) {
            for (String flag : Surf.config.antiIllegalIllegalItemFlagList()) {
                if (i.getItemMeta().hasItemFlag(ItemFlag.valueOf(flag))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasIllegalAttributes(ItemStack i) {
        if (i.hasItemMeta()) {
            for (String attribute : Surf.config.antiIllegalIllegalAttributeModifierList()) {
                if (i.getItemMeta().getAttributeModifiers(Attribute.valueOf(attribute)) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasIllegalTag(ItemStack i) {
        return false;
    }

    private static Map<String, Integer> initIllegalEnchantMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        Surf.config.antiIllegalIllegalEnchantList().forEach(ench -> {
            String[] list = ench.split(":");
            map.put(list[0], Integer.valueOf(list[1]));
        });

        return map;
    }

    public static boolean isIllegal(ItemStack i) {
        return isIllegalBlock(i) || isEnchantedBlock(i) || isIllegalPotion(i) || hasIllegalDurability(i)
                || isUnbreakable(i) || hasIllegalEnchants(i) || hasIllegalItemFlag(i) || hasIllegalAttributes(i);
    }

    public static void cleanIllegals(Inventory inventory) {
        // if inventory is empty, skip
        if (inventory.getContents().length == 0) return;

        ItemStack[] contents = inventory.getContents();
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
        if (i == null || isAir(i) || isIllegalBlock(i)) return new ItemStack(Material.AIR);

        if (Surf.config.antiIllegalDeleteIllegalsWhenFoundEnabled()) {
            if (ItemUtil.isEnchantedBlock(i) || ItemUtil.hasIllegalDurability(i) || ItemUtil.isUnbreakable(i)
                    || ItemUtil.hasIllegalEnchants(i) || ItemUtil.hasIllegalItemFlag(i) || ItemUtil.hasIllegalAttributes(i)) {
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
                    if (illegalEnchants.containsKey(key) && illegalEnchants.get(key) > 0 && level > illegalEnchants.get(key)) {
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
            for (String flag : Surf.config.antiIllegalIllegalItemFlagList()) {
                if (meta.hasItemFlag(ItemFlag.valueOf(flag))) {
                    meta.removeItemFlags(ItemFlag.valueOf(flag));
                }
            }

            // Clean illegal AttributeModifier
            for (String attribute : Surf.config.antiIllegalIllegalAttributeModifierList()) {
                if (meta.getAttributeModifiers(Attribute.valueOf(attribute)) != null) {
                    meta.removeAttributeModifier(Attribute.valueOf(attribute));
                }
            }

            i.setItemMeta(meta); // Remember to set cleaned meta back
        }

        return i;
    }
}