package org.surf.modules.antiillegal;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.surf.Main;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

public class ItemUtils {
    private final static Main plugin = Main.getInstance();

    public final static Set<Material> ILLEGALMATERIALS = new HashSet<>();

    public static void loadIllegalMaterials() {
        ILLEGALMATERIALS.clear();
        List<String> items = ConfigCache.AntiillegalIllegalItemsList;
        for (String item : items) {
            Material material = Material.getMaterial(item);
            if (material == null) {
                plugin.getLogger().log(Level.WARNING, "Invalid material: " + item);
                continue;
            }
            ILLEGALMATERIALS.add(material);
        }

    }

    public static boolean isIllegal(ItemStack item) {
        return ILLEGALMATERIALS.contains(item.getType());
    }

    public static boolean hasIllegalItemFlag(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) || meta.hasItemFlag(ItemFlag.HIDE_DESTROYS) || meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) || meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON) || meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS) || meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) || meta.isUnbreakable();
        }
        return false;
    }

    public static boolean hasIllegalEnchants(ItemStack item) {
        Map<Enchantment, Integer> enchants = item.getEnchantments();
        for (int level : enchants.values()) {
            return level > ConfigCache.IllegalEnchantsThreshold;
        }
        return false;
    }

    public static boolean isEnchantedBlock(ItemStack item) {
        if (item.getType().isBlock()) {
            if (item.hasItemMeta()) {
                return item.getItemMeta().hasEnchants();
            }
        }
        return false;
    }

    public static void deleteIllegals(Inventory inventory) {
        // TODO: use a list to store the items to delete
        ItemStack itemStack = null;
        boolean illegalsFound = false;
        // if inventory is empty, skip
        if (inventory.getContents().length == 0) {
            return;
        }
        for (ItemStack item : inventory.getContents()) {
            // code from https://github.com/moom0o/AnarchyExploitFixes/blob/b82a47bc23462900ece0ec3c30cfce0b25ff36f9/src/main/java/me/moomoo/anarchyexploitfixes/Main.java#L299
            if (!(item == null) && item.getType().name().equals("GOLDEN_APPLE")) {
                if (item.getData().toString().equals("GOLDEN_APPLE(0)") || item.getData().toString().equals("GOLDEN_APPLE(1)") || item.getData().toString().equals("GOLDEN_APPLE0")) {
                    return;
                }
            }
            // if item is null, skip
            if (item == null) {
                continue;
            }
            if (item.getDurability() > item.getType().getMaxDurability()) {
                item.setDurability(item.getType().getMaxDurability());
                itemStack = item;
            }
            if (item.getDurability() < 0) {
                item.setDurability((short) 1);
                itemStack = item;
            }

            if (isIllegal(item)) {
                inventory.remove(item);
                illegalsFound = true;
                itemStack = item;
                continue;
            }
            if (hasIllegalItemFlag(item)) {
                inventory.remove(item);
                illegalsFound = true;
                itemStack = item;
                continue;
            }
            if (hasIllegalEnchants(item)) {
                for (Entry<Enchantment, Integer> enchantmentIntegerEntry : item.getEnchantments().entrySet()) {
                    item.removeEnchantment(enchantmentIntegerEntry.getKey());
                    illegalsFound = true;
                    itemStack = item;
                }
            }
            if (item.hasItemMeta()) {
                if (isEnchantedBlock(item)) {
                    Iterator<Entry<Enchantment, Integer>> enchants = item.getEnchantments().entrySet()
                            .iterator();
                    illegalsFound = true;
                    itemStack = item;
                    while (enchants.hasNext()) {
                        item.removeEnchantment(enchants.next().getKey());
                    }
                }
            }
        }
        if (illegalsFound) {
            Utils.println(Utils.getPrefix() + "&6Deleted illegals " + itemStack.getType() + " " + itemStack.getI18NDisplayName() + " " + itemStack.getEnchantments());
        }
    }

}