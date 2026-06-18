package cn.dreeam.surf.modules.checks;

import org.bukkit.inventory.ItemStack;

public class ItemCheckHandler {

    public static void scanItem(ItemStack i) {
        for (ItemCheck itemCheck : ItemCheckRegistry.activeChecks()) {
            final boolean ret = itemCheck.doCheck(i);

            // TODO
        }
    }
}
