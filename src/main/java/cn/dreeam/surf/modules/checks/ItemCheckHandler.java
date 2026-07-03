package cn.dreeam.surf.modules.checks;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.perm.PermissionManager;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemCheckHandler {

    public static void scanInv(Inventory inv, Entity entity, String name) {
        ItemStack[] contents = inv.getContents();

        // if inventory is empty, skip
        if (contents.length == 0) return;

        // Only check permissions for the real player
        Player player = entity instanceof Player serverPlayer ? serverPlayer : null;

        PermissionManager.updateBypassStates(player);

        for (ItemStack item : contents) {
            // if item is null, skip
            if (item == null || ItemUtil.isAir(item)) continue;

            String originalItemName = ItemUtil.getItemDisplayName(item);

            scanItemOrReact(item, player);

            MessageUtil.println(String.format(
                    "&6Detected illegals %s on %s",
                    originalItemName,
                    name
            ));
        }
    }

    public static boolean scanSingleItemOrReact(ItemStack i, Entity entity) {
        // Only check permissions for the real player
        Player player = entity instanceof Player serverPlayer ? serverPlayer : null;

        PermissionManager.updateBypassStates(player);

        return scanItemOrReact(i, player);
    }

    // TODO: We can re-use the item meta and pass it to the `doSanitize` of next item check module
    // TODO: And setItemMeta back at the end of the iteration, so that prevent redunctant getItemMeta & setItemMeta in
    // TODO: every item check.
    // TODO: However, the design also needs to consider the direct NBT modifications (done by NBT API) in future item checks later

    private static boolean scanItemOrReact(ItemStack i, Player player) {
        final CheckResultAction action = Config.ItemChecks.checkResultAction;

        boolean isIllegal = false;

        for (ItemCheck itemCheck : ItemCheckRegistry.activeChecks()) {
            // TODO: implement permission bypass later
            if (player != null && itemCheck.canBypass(player)) continue;
            if (!itemCheck.appliesTo(i)) continue;

            isIllegal = itemCheck.doCheck(i);

            if (isIllegal) {
                if (action == CheckResultAction.REMOVE) {
                    i.setAmount(0);
                    return true;
                } else if (action == CheckResultAction.SANITIZE) {
                    itemCheck.doSanitize(i);
                }
            }
        }

        return isIllegal;
    }
}
