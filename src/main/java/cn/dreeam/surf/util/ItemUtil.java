package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ItemUtil {

    public static List<String> isBurrowBlock = Arrays.asList("ANVIL", "OBSIDIAN", "ENDER_CHEST");

    /*
    public static boolean isContainer(ItemStack i) {
        switch (i.getType()) {
            case
        }
        return ;
    }
     */

    public static boolean isIllegalTotem(ItemStack i) {
        return i.getType().equals(XMaterial.TOTEM_OF_UNDYING.parseMaterial()) && i.getAmount() > i.getMaxStackSize();
    }

    public static boolean isIllegalBlock(ItemStack i) {
        return i.getType().isBlock() && Surf.config.antiIllegalIllegalBlockList().contains(i.getType().toString());
    }

    public static boolean isEnchantedBlock(ItemStack i) {
        return i.getType().isBlock() && i.hasItemMeta() && i.getItemMeta().hasEnchants();
    }

    public static boolean hasIllegalDurability(ItemStack i) {
        return i.getDurability() > i.getType().getMaxDurability() || i.getDurability() < 0;
    }

    public static boolean hasIllegalEnchants(ItemStack i) {
        Map<Enchantment, Integer> enchants = i.getEnchantments();
        Map<String, Integer> illegalEnchants = initIllegalEnchantMap();
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

    private static void cleanDurability(ItemStack i) {
        if (i.getDurability() > i.getType().getMaxDurability()) {
            i.setDurability(i.getType().getMaxDurability());
        }

        if (i.getDurability() < 0) {
            i.setDurability((short) 1);
        }
    }

    private static void cleanEnchants(ItemStack i) {
        Map<Enchantment, Integer> enchants = i.getEnchantments();
        Map<String, Integer> illegalEnchants = initIllegalEnchantMap();
        for (Enchantment ench : enchants.keySet()) {
            String key = ench.getKey().getKey();
            int level = enchants.get(ench);
            if (illegalEnchants.containsKey(key)
                    && illegalEnchants.get(key) > 0
                    && level > illegalEnchants.get(key)) {
                i.addEnchantment(ench, ench.getMaxLevel());
            }
        }
    }

    private static void cleanItemFlags(ItemStack i) {
        if (i.hasItemMeta()) {
            for (String flag : Surf.config.antiIllegalIllegalItemFlagList()) {
                if (i.getItemMeta().hasItemFlag(ItemFlag.valueOf(flag))) {
                    i.getItemMeta().removeItemFlags(ItemFlag.valueOf(flag));
                }
            }
        }
    }

    private static void cleanAttributes(ItemStack i) {
        if (i.hasItemMeta()) {
            for (String attribute : Surf.config.antiIllegalIllegalAttributeModifierList()) {
                if (i.getItemMeta().getAttributeModifiers(Attribute.valueOf(attribute)) != null) {
                    i.getItemMeta().removeAttributeModifier(Attribute.valueOf(attribute));
                }
            }
        }
    }

    private static void cleanEnchantedBlock(ItemStack i) {
        if (i.getType().isBlock() && i.hasItemMeta() && i.getItemMeta().hasEnchants()) {
            Map<Enchantment, Integer> enchants = i.getEnchantments();
            enchants.keySet().forEach(i::removeEnchantment);
        }
    }

    public static void deleteIllegals(Inventory inventory) {
        ItemStack newItem = null;
        AtomicBoolean illegalsFound = new AtomicBoolean(false);

        // if inventory is empty, skip
        if (inventory.getContents().length == 0) return;

        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            // if item is null, skip
            if (item == null) {
                continue;
            }

            newItem = deleteIllegals(item);

            if (newItem != null && !newItem.equals(item)) {
                inventory.setItem(i, newItem);
                illegalsFound.set(true);
            }

            if (illegalsFound.get()) {
                Util.println(Util.getPrefix() + "&6Deleted illegals " + item.getType() + " " + item.getI18NDisplayName() + " " + item.getEnchantments() + (item.hasItemMeta() ? " " + item.getItemMeta().getAttributeModifiers() : ""));
            }
        }
    }

    private static ItemStack deleteIllegals(ItemStack i) {
        if (i == null || i.getType().isAir()) return i;

        if (i.getType().isBlock() && (isIllegalBlock(i) || isEnchantedBlock(i))) { // Dreeam TODO
            return new ItemStack(Material.AIR);
        }

        if (true) { // Dreeam TODO - Configurable
            cleanDurability(i);
            cleanEnchants(i);
            cleanItemFlags(i);
            cleanAttributes(i);
            cleanEnchantedBlock(i);
        } else {
            if (hasIllegalDurability(i)
                    || hasIllegalItemFlag(i) || hasIllegalAttributes(i) || hasIllegalEnchants(i)) {
                return new ItemStack(Material.AIR);
                //i.setAmount(0); // need to try??
            }
        }

        return i;
    }
}