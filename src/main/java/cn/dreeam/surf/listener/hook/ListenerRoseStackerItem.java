package cn.dreeam.surf.listener.hook;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheckHandler;
import cn.dreeam.surf.util.MessageUtil;
import dev.rosewood.rosestacker.event.ItemPickupEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ListenerRoseStackerItem implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onPickup(ItemPickupEvent event) {
        if (!Config.ItemChecks.checkTriggerOnPickup) return;

        ItemStack i = event.getStackedItem().getItem().getItemStack();
        Entity entity = event.getEntity();

        if (ItemCheckHandler.scanItemOrReact(i, entity)) {
            event.setCancelled(true);
            event.getStackedItem().getItem().remove();
            if (entity instanceof Player player) {
                MessageUtil.sendMessage(player, "&6You can not pick up this illegal item.");
            } else {
                MessageUtil.println(String.format(
                        "%s try to pick up an illegal item at %s",
                        event.getEntity().getName().toLowerCase(),
                        MessageUtil.locToString(event.getStackedItem().getLocation())
                ));
            }
        }
    }
}
