package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class StackedTotem {

    public static void revertPeriodically() {
        if (!Surf.config.stackedTotemRevertPeriodicallyEnabled()) return;

        Surf.getInstance().foliaLib.getImpl().runTimer(() -> Bukkit.getWorlds().forEach(b -> b.getPlayers().forEach(e -> e.getInventory().forEach(item -> {
            if (item != null) {
                if (item.getType() == Material.TOTEM_OF_UNDYING && item.getAmount() > item.getMaxStackSize()) {
                    item.setAmount(item.getMaxStackSize());
                }
            }
        }))), 0L, 20L);
    }
}
