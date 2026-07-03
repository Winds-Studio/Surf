package cn.dreeam.surf.perm;

import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.modules.checks.ItemCheckRegistry;
import org.bukkit.entity.Player;

public class PermissionManager {

    // Update bypass permissions only once under each event trigger.
    // Intends to prevent checking player permissions when every item check module has been called.
    //
    // Since Spigot/Paper doesn't provide a permission update event, we can only use this way
    // to reduce the calling count of `Player#hasPermission`.
    // And the permission check is hashmap lookup that the map uses the String as the key.
    // So I prefer to use this way to reduce cost as much as it can until the permission storage is optimized.
    public static void updateBypassStates(Player player) {
        if (player == null) return;

        for (ItemCheck check : ItemCheckRegistry.activeChecks()) {
            check.updateBypassableState(player);
        }
    }
}
