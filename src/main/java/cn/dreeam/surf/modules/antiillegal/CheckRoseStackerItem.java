package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.Util;
import dev.rosewood.rosestacker.event.ItemPickupEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CheckRoseStackerItem implements Listener {

    @EventHandler(ignoreCancelled = true)
    @AntiIllegal(EventName = "ItemPickupEvent")
    public void onPickup(ItemPickupEvent event) {
        if (!Config.antiIllegalCheckWhenItemPickupEnabled) return;

        ItemStack i = event.getStackedItem().getItem().getItemStack();

        if (ItemUtil.isIllegal(i)) {
            event.setCancelled(true);
            event.getStackedItem().getItem().remove();
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
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
