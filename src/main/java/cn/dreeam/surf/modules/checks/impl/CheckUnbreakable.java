package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.modules.checks.SanitizeAction;
import cn.dreeam.surf.perm.PermissionNodes;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckUnbreakable implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM + "unbreakable";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleUnbreakable;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        return i.hasItemMeta();
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
        return i.getItemMeta().isUnbreakable();
    }

    @Override
    public SanitizeAction doSanitize(ItemStack i) {
        final ItemMeta meta = i.getItemMeta();

        meta.setUnbreakable(false);

        i.setItemMeta(meta);
        return SanitizeAction.SANITIZED;
    }
}
