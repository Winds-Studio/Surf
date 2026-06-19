package cn.dreeam.surf.modules.checks.impl.speific;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class CheckPotion implements ItemCheck {

    @Override
    public boolean enabled() {
        return Config.checkItemPotion;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        // TODO, use == potion
        return i.getType().toString().contains("POTION") && i.hasItemMeta();
    }

    @Override
    public boolean canBypass() {
        // TODO
        throw new UnsupportedOperationException();
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
