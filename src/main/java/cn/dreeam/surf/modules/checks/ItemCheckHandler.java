package cn.dreeam.surf.modules.checks;

import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.inventory.ItemStack;

public class ItemCheckHandler {

    public static void scanItem(ItemStack i) {
        if (i == null || ItemUtil.isAir(i)) return;

        for (ItemCheck itemCheck : ItemCheckRegistry.activeChecks()) {
            final boolean ret = itemCheck.doCheck(i);

            // TODO: choose method defined in the config, remove entire item / sanitize illegal data / whether log
            if (ret) {
                i.setAmount(0);
            }
        }
    }
}
