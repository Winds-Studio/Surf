package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.modules.checks.ItemCheck;
import org.bukkit.inventory.ItemStack;

public class CheckStackAmount implements ItemCheck {

    @Override
    public boolean enabled() {
        return configEnabled;
    }

    @Override
    public boolean canBypass() {
        return false;
    }

    @Override
    public boolean doCheck(ItemStack i) {
        return i.getAmount() > i.getMaxStackSize();
    }
}
