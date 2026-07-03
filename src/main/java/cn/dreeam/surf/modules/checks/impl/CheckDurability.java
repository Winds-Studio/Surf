package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.perm.PermissionNodes;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckDurability implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM + "durability";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleDurability;
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
        final short durability = i.getDurability();
        return durability > i.getType().getMaxDurability()
                || durability < 0;
    }

    @Override
    public void doSanitize(ItemStack i) {
        final short durability = i.getDurability();
        if (durability > i.getType().getMaxDurability()) {
            i.setDurability(i.getType().getMaxDurability());
        }

        if (durability < 0) {
            i.setDurability((short) 1);
        }
    }
}
