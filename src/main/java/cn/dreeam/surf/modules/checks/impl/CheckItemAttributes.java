package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.modules.checks.SanitizeAction;
import cn.dreeam.surf.perm.PermissionNodes;
import cn.dreeam.surf.util.PlatformUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckItemAttributes implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM + "attributes";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleAttributes && PlatformUtil.isNewerAndEqual(13, 0);
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
    public SanitizeAction doSanitize(ItemStack i) {
        // TODO check
        final ItemMeta meta = i.getItemMeta();

        for (Attribute attribute : ItemUtil.illegalAttributes) {
            if (meta.getAttributeModifiers(attribute) != null) {
                meta.removeAttributeModifier(attribute);
            }
        }

        i.setItemMeta(meta); // Remember to set cleaned meta back
        return SanitizeAction.SANITIZED;
    }
}
