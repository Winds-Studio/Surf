package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class StackedTotem implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!Config.stackedTotemRevertAsOneEnabled) return;

        // Check when player gets damage or make damage to others
        if (event.getEntity() instanceof Player) {
            Player entity = (Player) event.getEntity();

            entity.getInventory().forEach(i -> {
                        if (i != null && ItemUtil.isIllegalTotem(i)) {
                            i.setAmount(1);
                        }
                    }
            );
        }
    }
}
