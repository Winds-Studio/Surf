package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.modules.checks.SanitizeAction;
import cn.dreeam.surf.perm.PermissionNodes;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckAmount implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM + "amount";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleAmount;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        return !ItemUtil.checkRuleAmountWhitelistMaterials.isEmpty() && ItemUtil.checkRuleAmountWhitelistMaterials.contains(i.getType());
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
        return i.getAmount() > i.getMaxStackSize();
    }

    @Override
    public SanitizeAction doSanitize(ItemStack i) {
        i.setAmount(i.getMaxStackSize());
        return SanitizeAction.SANITIZED;
    }
}
