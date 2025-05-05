package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class IllegalBlockCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onBlockPlace(BlockPlaceEvent event) {
        if (!Config.antiIllegalCheckIllegalBlockEnabled) return;

        if (ItemUtil.isIllegalItem(event.getItemInHand())) {
            Player player = event.getPlayer();

            event.setCancelled(true);

            if (event.getHand() == EquipmentSlot.HAND) {
                player.getInventory().setItemInMainHand(null);
            } else {
                player.getInventory().setItemInOffHand(null);
            }

            MessageUtil.sendMessage(player, Config.antiIllegalCheckIllegalBlockMessage);
        }
    }
}
