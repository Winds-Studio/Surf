package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckAmount implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleAmount;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        return !ItemUtil.checkRuleAmountWhitelistMaterials.isEmpty() && ItemUtil.checkRuleAmountWhitelistMaterials.contains(i.getType());
    }

    @Override
    public boolean canBypass(Player player) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doCheck(ItemStack i) {
        return i.getAmount() > i.getMaxStackSize();
    }

    @Override
    public void doSanitize(ItemStack i) {
        i.setAmount(i.getMaxStackSize());
    }
}
