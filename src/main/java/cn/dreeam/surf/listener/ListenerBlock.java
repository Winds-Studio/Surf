package cn.dreeam.surf.listener;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.CheckResultAction;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ListenerBlock implements Listener {

    @EventHandler(ignoreCancelled = true)
    private void onPlace(BlockPlaceEvent event) {
        if (!Config.antiIllegalCheckIllegalBlockEnabled) return;

        if (ItemUtil.isIllegalItem(event.getItemInHand())) {
            final Player player = event.getPlayer();
            final CheckResultAction action = Config.ItemChecks.checkResultAction;

            event.setCancelled(true);

            // Treat SANITIZE as canceling event (above)
            if (action == CheckResultAction.REMOVE) {
                if (event.getHand() == EquipmentSlot.HAND) {
                    player.getInventory().setItemInMainHand(null);
                } else {
                    player.getInventory().setItemInOffHand(null);
                }
            }

            MessageUtil.sendMessage(player, Config.antiIllegalCheckIllegalBlockMessage);
        }
    }
}
