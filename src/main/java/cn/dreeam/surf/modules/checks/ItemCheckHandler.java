package cn.dreeam.surf.modules.checks;

import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemCheckHandler {

    public static void scanInv(Inventory inv, String name) {
        ItemStack[] contents = inv.getContents();

        // if inventory is empty, skip
        if (contents.length == 0) return;

        for (ItemStack item : contents) {
            // if item is null, skip
            if (item == null || ItemUtil.isAir(item)) continue;

            String originalItemName = ItemUtil.getItemDisplayName(item);

            scanItemOrReact(item);

            MessageUtil.println(String.format(
                    "&6Detected illegals %s on %s",
                    originalItemName,
                    name
            ));
        }
    }

    public static boolean scanItem(ItemStack i) {
        for (ItemCheck itemCheck : ItemCheckRegistry.activeChecks()) {
            final boolean ret = itemCheck.doCheck(i);

            if (ret) {
                return true;
            }
        }
        return false;
    }

    private static void scanItemOrReact(ItemStack i) {
        for (ItemCheck itemCheck : ItemCheckRegistry.activeChecks()) {
            final boolean ret = itemCheck.doCheck(i);

            // TODO: choose method defined in the config, remove entire item / sanitize illegal data / whether log
            if (ret) {
                i.setAmount(0);
            }
        }
    }
}
