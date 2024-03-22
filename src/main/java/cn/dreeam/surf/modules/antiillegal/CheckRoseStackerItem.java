package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
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
        if (!Surf.config.antiIllegalCheckWhenItemPickupEnabled()) return;

        ItemStack i = event.getStackedItem().getItem().getItemStack();

        if (ItemUtil.isIllegalBlock(i) || ItemUtil.isEnchantedBlock(i)
                || ItemUtil.hasIllegalDurability(i) || ItemUtil.isUnbreakable(i) || ItemUtil.hasIllegalEnchants(i)
                || ItemUtil.hasIllegalItemFlag(i) || ItemUtil.hasIllegalAttributes(i)) {
            event.setCancelled(true);
            event.getStackedItem().getItem().remove();
            if (event.getEntity() instanceof Player) {
                Util.sendMessage(((Player) event.getEntity()), "You can not pick up this illegal item.");
            } else {
                Util.println(event.getEntity().getName() + " Try to pick up an illegal item at " + event.getStackedItem().getLocation());
            }
        }
    }
}
