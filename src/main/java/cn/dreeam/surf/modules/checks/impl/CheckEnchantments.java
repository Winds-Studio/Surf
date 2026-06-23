package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CheckEnchantments implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.checkRuleEnchantments;
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
        final Map<String, Integer> illegalEnchants = ItemUtil.illegalEnchantsMap;

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

    @Override
    public void doSanitize(ItemStack i) {
        // TODO check
        final Map<Enchantment, Integer> enchants = i.getEnchantments();
        final Map<String, Integer> illegalEnchants = ItemUtil.illegalEnchantsMap;

        if (i.getType().isBlock()) {
            if (Config.antiIllegalRemoveBlockEnchant) {
                // Directly remove enchs
                enchants.keySet().forEach(i::removeEnchantment);
            } else {
                // Correct enchs
                enchants.keySet().forEach(ench -> {
                    String key = ench.getKey().getKey();
                    int level = enchants.get(ench);
                    if (illegalEnchants.containsKey(key) && illegalEnchants.get(key) > 0 && level > illegalEnchants.get(key)) {
                        i.addUnsafeEnchantment(ench, ench.getMaxLevel());
                    }
                });
            }
        } else {
            enchants.keySet().forEach(ench -> {
                if (Config.antiIllegalAllowInapplicableEnchant || ench.canEnchantItem(i)) {
                    String key = ench.getKey().getKey();
                    int level = enchants.get(ench);
                    if (illegalEnchants.containsKey(key) && illegalEnchants.get(key) > 0 && level > illegalEnchants.get(key)) {
                        i.addUnsafeEnchantment(ench, ench.getMaxLevel());
                    }
                } else {
                    // Should remove conflict/incompatible ench, since the item is illegal.
                    i.removeEnchantment(ench);
                }
            });
        }
    }
}
