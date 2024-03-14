package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class IllegalBlockCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!Surf.config.antiIllegalCheckIllegalBlockEnabled()) return;

        if (ItemUtil.isIllegalBlock(event.getItemInHand())) {
            Player player = event.getPlayer();

            event.setCancelled(true);

            if (event.getHand() == EquipmentSlot.HAND) {
                player.getInventory().setItemInMainHand(null);
            } else {
                player.getInventory().setItemInOffHand(null);
            }

            Util.sendMessage(player, Surf.config.antiIllegalCheckIllegalBlockMessage());
        }
    }
}
