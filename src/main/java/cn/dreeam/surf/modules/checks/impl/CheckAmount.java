package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import org.bukkit.inventory.ItemStack;

public class CheckAmount implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.checkItemAmount;
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
        return i.getAmount() > i.getMaxStackSize();
    }

    @Override
    public void doSanitize(ItemStack i) {
        i.setAmount(i.getMaxStackSize());
    }
}
