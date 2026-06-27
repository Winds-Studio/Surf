package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.util.item.ItemUtil;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CheckEnchantments implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleEnchantments;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        return true;
    }

    @Override
    public boolean canBypass() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doCheck(ItemStack i) {
        // TODO check
        final Map<Enchantment, Integer> enchants = i.getEnchantments();
        final Object2IntOpenHashMap<Enchantment> illegalEnchants = ItemUtil.maxEnchantLevels;

        for (Enchantment enchantment : enchants.keySet()) {
            int level = enchants.get(enchantment);
            int maxLevel = illegalEnchants.getInt(enchantment);

            if (maxLevel > 0 && level > maxLevel) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void doSanitize(ItemStack i) {
        // TODO check
        final Map<Enchantment, Integer> enchants = i.getEnchantments();
        final Object2IntOpenHashMap<Enchantment> illegalEnchants = ItemUtil.maxEnchantLevels;

        if (i.getType().isBlock()) {
            if (Config.antiIllegalRemoveBlockEnchant) {
                // Directly remove enchantments
                enchants.keySet().forEach(i::removeEnchantment);
            } else {
                // Correct enchantments
                enchants.keySet().forEach(enchant -> {
                    final int level = enchants.get(enchant);
                    final int maxLevel = illegalEnchants.getInt(enchant);

                    if (maxLevel > 0 && level > maxLevel) {
                        i.addUnsafeEnchantment(enchant, enchant.getMaxLevel());
                    }
                });
            }
        } else {
            enchants.keySet().forEach(enchant -> {
                if (Config.antiIllegalAllowInapplicableEnchant || enchant.canEnchantItem(i)) {
                    final int level = enchants.get(enchant);
                    final int maxLevel = illegalEnchants.getInt(enchant);
                    if (maxLevel > 0 && level > maxLevel) {
                        i.addUnsafeEnchantment(enchant, enchant.getMaxLevel());
                    }
                } else {
                    // Should remove conflict/incompatible ench, since the item is illegal.
                    i.removeEnchantment(enchant);
                }
            });
        }
    }
}
