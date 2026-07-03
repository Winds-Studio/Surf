package cn.dreeam.surf.modules.checks.impl;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.perm.PermissionNodes;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckItemFlags implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM + "itemflags";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleFlags;
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
        final boolean isBanner = i.getType().toString().contains("BANNER");

        for (ItemFlag flag : ItemUtil.illegalItemFlags) {
            // Skip ominous banner
            if (isBanner && flag.name().equalsIgnoreCase("HIDE_ADDITIONAL_TOOLTIP")) {
                continue;
            }

            if (i.getItemMeta().hasItemFlag(flag)) {
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

        for (ItemFlag flag : ItemUtil.illegalItemFlags) {
            // Skip ominous banner
            if (isBanner && flag.name().equalsIgnoreCase("HIDE_ADDITIONAL_TOOLTIP")) {
                continue;
            }

            if (meta.hasItemFlag(flag)) {
                meta.removeItemFlags(flag);
            }
        }

        i.setItemMeta(meta); // Remember to set cleaned meta back
    }
}
