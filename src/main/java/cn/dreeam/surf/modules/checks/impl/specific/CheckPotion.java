package cn.dreeam.surf.modules.checks.impl.specific;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.perm.PermissionNodes;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class CheckPotion implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM_SPECIFIC + "potion";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRulePotion;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        return ItemUtil.isPotion(i) && i.hasItemMeta();
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
        final PotionMeta pot = (PotionMeta) i.getItemMeta();

        for (PotionEffect effect : pot.getCustomEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void doSanitize(ItemStack i) {
        // TODO delete potion directly.
        throw new UnsupportedOperationException();
    }
}
