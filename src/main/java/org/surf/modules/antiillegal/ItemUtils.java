package org.surf.modules.antiillegal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.surf.Main;
import org.surf.util.Utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

public class ItemUtils {
    private final static Main plugin = Main.getInstance();

    public static boolean isIllegal(ItemStack item) {
        List<String> items = plugin.getConfig().getStringList("Antiillegal.Illegal-Items-List");
        List<Material> materials = new ArrayList<>();
        List<Material> knownMaterials = Arrays.asList(Material.values());
        for (String material : items) {
            String upperCaseMat = material.toUpperCase();
            if (knownMaterials.contains(Material.getMaterial(upperCaseMat))) {
                materials.add(Material.getMaterial(upperCaseMat));
            } else {
                plugin.getLogger().log(Level.SEVERE, ChatColor.translateAlternateColorCodes('&', "&cInvalid configuration option Antiillegal.Illegal-Items-List " + material));
            }
        }
        return materials.contains(item.getType());
    }

    public static boolean hasIllegalItemFlag(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) || meta.hasItemFlag(ItemFlag.HIDE_DESTROYS) || meta.hasItemFlag(ItemFlag.HIDE_DYE) || meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) || meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON) || meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS) || meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE) || meta.isUnbreakable();
        }
        return false;
    }

    // TODO - Add ability to filter for custom NBT attributes[configurable]
    public static boolean hasIllegalAttributes(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasAttributeModifiers();
        }
        return false;
    }

    public static boolean hasIllegalEnchants(ItemStack item) {
        Map<Enchantment, Integer> enchants = item.getEnchantments();
        for (int level : enchants.values()) {
            return level > plugin.getConfig().getInt("IllegalEnchants.Threshold");
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
            if (hasIllegalAttributes(item)) {
                inventory.remove(item);
                //TODO
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_FLYING_SPEED);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_FOLLOW_RANGE);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_LUCK);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_MAX_HEALTH);
//                            item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED);
//                            item.getItemMeta().removeAttributeModifier(Attribute.HORSE_JUMP_STRENGTH);
//                            item.getItemMeta().removeAttributeModifier(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS);
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
            Utils.println(Utils.getPrefix() + "&6Deleted illegals " + itemStack.getType() + " " + itemStack.getI18NDisplayName() + " " + itemStack.getEnchantments() + (itemStack.hasItemMeta() ? " " + itemStack.getItemMeta().getAttributeModifiers() : ""));
        }
    }

}