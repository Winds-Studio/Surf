package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class StackedTotem implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!Surf.config.stackedTotemRevertAsOneEnabled()) return;

        if (event.getEntity() instanceof Player) {
            Player damager = (Player) event.getEntity();
            ItemStack item = damager.getInventory().getItemInMainHand();
            ItemStack item2 = damager.getInventory().getItemInOffHand();

            if (!ItemUtil.isIllegalTotem(item) && !ItemUtil.isIllegalTotem(item2)) return;

            damager.getInventory().forEach(i -> {
                        if (i != null && ItemUtil.isIllegalTotem(i)) {
                            i.setAmount(1);
                        }
                    }
            );
        }
    }
}
