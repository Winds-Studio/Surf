package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckItemFlags implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.checkRuleFlags;
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
        // TODO check
        final boolean isBanner = i.getType().toString().contains("BANNER");

        for (String flag : Config.checkDefinitionIllegalItemFlags) {
            // Skip ominous banner
            if (isBanner && flag.equalsIgnoreCase("HIDE_ADDITIONAL_TOOLTIP")) {
                continue;
            }

            if (i.getItemMeta().hasItemFlag(ItemFlag.valueOf(flag))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doSanitize(ItemStack i) {
        // TODO check
        final ItemMeta meta = i.getItemMeta();
        final boolean isBanner = i.getType().toString().contains("BANNER");

        for (String flag : Config.checkDefinitionIllegalItemFlags) {
            // Skip ominous banner
            if (isBanner && flag.equalsIgnoreCase("HIDE_ADDITIONAL_TOOLTIP")) {
                continue;
            }

            final ItemFlag itemFlag = ItemFlag.valueOf(flag);

            if (meta.hasItemFlag(itemFlag)) {
                meta.removeItemFlags(itemFlag);
            }
        }

        i.setItemMeta(meta); // Remember to set cleaned meta back
    }
}
