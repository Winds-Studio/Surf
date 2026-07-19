package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.modules.checks.SanitizeAction;
import cn.dreeam.surf.perm.PermissionNodes;
import cn.dreeam.surf.util.item.ItemUtil;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CheckEnchantments implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM + "enchantments";

    private boolean canBypass;

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
        return canBypass;
    }

    @Override
    public void updateBypassableState(Player player) {
        canBypass = player.hasPermission(PERM);
    }

    @Override
    public boolean doCheck(ItemStack i) {
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
    public SanitizeAction doSanitize(ItemStack i) {
        final Map<Enchantment, Integer> enchants = i.getEnchantments();
        final Object2IntOpenHashMap<Enchantment> illegalEnchants = ItemUtil.maxEnchantLevels;

        if (i.getType().isBlock()) {
            if (Config.ItemChecks.checkRuleEnchantmentsNoEnchantOnBlock) {
                enchants.keySet().forEach(i::removeEnchantment);
            } else {
                // Correct enchantments
                // We don't need check canEnchantItem since we allow enchants on block here
                enchants.forEach((enchant, level) -> {
                    final int maxLevel = illegalEnchants.getInt(enchant);

                    if (maxLevel > 0 && level > maxLevel) {
                        i.addUnsafeEnchantment(enchant, enchant.getMaxLevel());
                    }
                });
            }
        } else {
            final boolean allowInapplicable = Config.ItemChecks.checkRuleEnchantmentsAllowInapplicableEnchant;

            enchants.forEach((enchant, level) -> {
                if (allowInapplicable || enchant.canEnchantItem(i)) {
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
        return SanitizeAction.SANITIZED;
    }
}
