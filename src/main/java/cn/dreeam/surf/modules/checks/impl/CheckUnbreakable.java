package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckUnbreakable implements ItemCheck {

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
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean doCheck(ItemStack i) {
        return i.getItemMeta().isUnbreakable();
    }

    @Override
    public void doSanitize(ItemStack i) {
        final ItemMeta meta = i.getItemMeta();

        meta.setUnbreakable(false);

        i.setItemMeta(meta);
    }
}
