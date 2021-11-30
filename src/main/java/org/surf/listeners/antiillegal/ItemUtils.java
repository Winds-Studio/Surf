package org.surf.listeners.antiillegal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.surf.Main;
import org.surf.util.Utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

public class    ItemUtils {
    Main plugin;

    public ItemUtils(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isArmor(ItemStack item) {
        List<Material> armor = Arrays.asList(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS,
                Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS,
                Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS,
                Material.CHAINMAIL_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.GOLD_CHESTPLATE,
                Material.DIAMOND_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_HELMET,
                Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET,
                Material.ELYTRA);
        return armor.contains(item.getType());
    }

    public boolean isTool(ItemStack item) {
        List<Material> tools = Arrays.asList(Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SWORD,
                Material.DIAMOND_HOE, Material.DIAMOND_SPADE, Material.IRON_AXE, Material.IRON_HOE,
                Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD, Material.GOLD_AXE, Material.GOLD_HOE,
                Material.GOLD_PICKAXE, Material.GOLD_SWORD, Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE,
                Material.WOOD_SWORD, Material.CARROT_STICK, Material.SHEARS, Material.FISHING_ROD, Material.BOW,
                Material.FLINT_AND_STEEL);
        return tools.contains(item.getType());
    }

    public boolean isIllegal(ItemStack item) {
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

    public boolean hasIllegalNBT(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            return meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) || meta.hasItemFlag(ItemFlag.HIDE_DESTROYS)
                    || meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) || meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON)
                    || meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS) || meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                    || meta.isUnbreakable() || meta.hasLore();
        }
        return false;
    }

    public boolean isOverstacked(ItemStack item) {
        if (plugin.getConfigBoolean("Antiillegal.Delete-Stacked-Items")) {
            return item.getAmount() > item.getMaxStackSize();
        } else {
            return false;
        }
    }

    public boolean hasIllegalEnchants(ItemStack item) {
        Map<Enchantment, Integer> enchants = item.getEnchantments();
        for (int level : enchants.values()) {
            return level > 5;
        }
        return false;
    }

    public boolean isEnchantedBlock(ItemStack item) {
        if (item.getType().isBlock()) {
            if (item.hasItemMeta()) {
                return item.getItemMeta().hasEnchants();
            }
        }
        return false;
    }

    public void deleteIllegals(Inventory inventory) {
        try {
            ItemUtils utils = plugin.getItemUtils();
            ItemStack itemStack = null;
            boolean illegalsFound = false;
            if (inventory.getContents() != null) {
                for (ItemStack item : inventory.getStorageContents()) {
                    if (item != null) {
                        if (utils.isArmor(item) || utils.isTool(item)) {
                            if (item.getDurability() > item.getType().getMaxDurability()) {
                                item.setDurability(item.getType().getMaxDurability());
                                itemStack = item;
                            }
                            if (item.getDurability() < 0) {
                                item.setDurability((short) 1);
                                itemStack = item;
                            }

                        }
                        if (utils.isIllegal(item)) {
                            inventory.remove(item);
                            illegalsFound = true;
                            itemStack = item;
                        }
                        if (utils.hasIllegalNBT(item)) {
                            inventory.remove(item);
                            illegalsFound = true;
                            itemStack = item;

                        }
                        if (utils.isOverstacked(item)) {
                            item.setAmount(item.getMaxStackSize());
                            itemStack = item;
                        }
                        if (utils.hasIllegalEnchants(item)) {
                            for (Entry<Enchantment, Integer> enchantmentIntegerEntry : item.getEnchantments().entrySet()) {
                                item.removeEnchantment(enchantmentIntegerEntry.getKey());
                                illegalsFound = true;
                                itemStack = item;
                            }
                        }
                        if (item.hasItemMeta()) {
                            ItemMeta meta = item.getItemMeta();
                            if (utils.isEnchantedBlock(item)) {
                                Iterator<Entry<Enchantment, Integer>> enchants = item.getEnchantments().entrySet()
                                        .iterator();
                                illegalsFound = true;
                                itemStack = item;
                                while (enchants.hasNext()) {
                                    item.removeEnchantment(enchants.next().getKey());
                                }
                            }
                            if (item.getItemMeta() instanceof BlockStateMeta) {
                                BlockStateMeta blockStateMeta = (BlockStateMeta) item.getItemMeta();
                                if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                                    ShulkerBox shulker = (ShulkerBox) blockStateMeta.getBlockState();
                                    for (ItemStack shulkerItem : shulker.getInventory().getContents()) {
                                        if (shulkerItem != null) {
                                            if (utils.isArmor(shulkerItem) || utils.isTool(shulkerItem)) {
                                                if (shulkerItem.getDurability() > shulkerItem.getType().getMaxDurability()) {
                                                    shulkerItem.setDurability(shulkerItem.getType().getMaxDurability());
                                                    illegalsFound = true;
                                                    itemStack = item;
                                                }
                                                if (shulkerItem.getDurability() < 0) {
                                                    shulkerItem.setDurability((short) 1);
                                                    illegalsFound = true;
                                                    itemStack = item;
                                                }
                                            }
                                            if (utils.isIllegal(shulkerItem)) {
                                                shulker.getInventory().remove(shulkerItem);
                                                illegalsFound = true;
                                                itemStack = item;
                                            }
                                            if (utils.hasIllegalNBT(shulkerItem)) {
                                                shulker.getInventory().remove(shulkerItem);
                                                illegalsFound = true;
                                                itemStack = item;
                                            }
                                            if (utils.isOverstacked(shulkerItem)) {
                                                shulkerItem.setAmount(shulkerItem.getType().getMaxStackSize());
                                                illegalsFound = true;
                                                itemStack = item;
                                            }
                                            if (utils.hasIllegalEnchants(shulkerItem)) {
                                                for (Entry<Enchantment, Integer> enchantmentIntEntry : shulkerItem.getEnchantments().entrySet()) {
                                                    if (enchantmentIntEntry.getValue() > enchantmentIntEntry.getKey().getMaxLevel()) {
                                                        if (enchantmentIntEntry.getKey().canEnchantItem(shulkerItem)) {
                                                            shulkerItem.addEnchantment(enchantmentIntEntry.getKey(), enchantmentIntEntry.getKey().getMaxLevel());
                                                        } else {
                                                            shulkerItem.removeEnchantment(enchantmentIntEntry.getKey());
                                                        }
                                                    }
                                                }
                                                illegalsFound = true;
                                                itemStack = item;
                                            }
                                            if (utils.isEnchantedBlock(shulkerItem)) {
                                                shulkerItem.removeEnchantment(shulkerItem.getEnchantments().entrySet().iterator().next().getKey());
                                                illegalsFound = true;
                                                itemStack = item;
                                            }
                                        }
                                    }
                                    blockStateMeta.setBlockState(shulker);
                                    item.setItemMeta(blockStateMeta);
                                }
                            }
                        }
                    }
                }
            }
            if (illegalsFound) {
                Utils.println(Utils.getPrefix() + "&6Deleted illegals " + itemStack.getType() + " " + itemStack.getI18NDisplayName() + " " + itemStack.getEnchantments());
            }
        } catch (Error | Exception throwable) {

        }
    }
}