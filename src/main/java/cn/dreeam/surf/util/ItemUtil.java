package cn.dreeam.surf.util;

import cn.dreeam.surf.Surf;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ItemUtil {

    public static boolean isContainer(ItemStack i) {
        switch (i.getType()) {
            case
        }
        return ;
    }

    public static boolean isIllegalBlock(ItemStack i) {
        return Surf.config.antiIllegalIllegalBlockList().contains(i.getType().toString());
    }

    public static boolean isIllegalTotem(ItemStack i) {
        return i.getType().equals(XMaterial.TOTEM_OF_UNDYING.parseMaterial()) || i.getAmount() > i.getMaxStackSize();
    }

    public static boolean isEnchantedBlock(ItemStack i) {
        return i.getType().isBlock() && i.hasItemMeta() && i.getItemMeta().hasEnchants();
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
            if (Surf.config.antiIllegalEnchantsThreshold() > 0 && level > Surf.config.antiIllegalEnchantsThreshold()) {
                return true;
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

            if (isIllegalBlock(item)) {
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
                /*
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_FLYING_SPEED);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_FOLLOW_RANGE);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_LUCK);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_MAX_HEALTH);
                item.getItemMeta().removeAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED);
                item.getItemMeta().removeAttributeModifier(Attribute.HORSE_JUMP_STRENGTH);
                item.getItemMeta().removeAttributeModifier(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS);
                 */
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
            Util.println(Util.getPrefix() + "&6Deleted illegals " + itemStack.getType() + " " + itemStack.getI18NDisplayName() + " " + itemStack.getEnchantments() + (itemStack.hasItemMeta() ? " " + itemStack.getItemMeta().getAttributeModifiers() : ""));
        }
    }
}