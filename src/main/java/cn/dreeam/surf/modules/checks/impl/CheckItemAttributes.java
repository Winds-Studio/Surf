package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.util.PlatformUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckItemAttributes implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.checkRuleAttributes && PlatformUtil.isNewerAndEqual(13, 0);
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
        final ItemMeta meta = i.getItemMeta();

        for (Attribute attribute : ItemUtil.illegalAttributes) {
            if (meta.getAttributeModifiers(attribute) != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void doSanitize(ItemStack i) {
        // TODO check
        final ItemMeta meta = i.getItemMeta();

        for (Attribute attribute : ItemUtil.illegalAttributes) {
            if (meta.getAttributeModifiers(attribute) != null) {
                meta.removeAttributeModifier(attribute);
            }
        }

        i.setItemMeta(meta); // Remember to set cleaned meta back
    }
}
