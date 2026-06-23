package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckItemAttributes implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.checkRuleAttributes;
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

        for (String attribute : Config.checkDefinitionIllegalAttributes) {
            if (meta.getAttributeModifiers(ItemUtil.getAttributeByName(attribute)) != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void doSanitize(ItemStack i) {
        // TODO check
        final ItemMeta meta = i.getItemMeta();

        for (String attributeStr : Config.checkDefinitionIllegalAttributes) {
            Attribute attribute = ItemUtil.getAttributeByName(attributeStr);

            if (attribute != null && meta.getAttributeModifiers(attribute) != null) {
                meta.removeAttributeModifier(attribute);
            }
        }

        i.setItemMeta(meta); // Remember to set cleaned meta back
    }
}
