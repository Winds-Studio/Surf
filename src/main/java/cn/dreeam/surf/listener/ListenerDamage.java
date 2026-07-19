package cn.dreeam.surf.listener;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.item.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerDamage implements Listener {

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (!Config.ItemChecks.checkRuleAmountCheckTotemSizeOnDamage) return;

        // Check when player gets damage or make damage to others
        if (event.getEntity() instanceof Player entity) {
            entity.getInventory().forEach(i -> {
                        if (i == null || ItemUtil.isAir(i)) return;

                        if (ItemUtil.isIllegalTotem(i)) {
                            i.setAmount(1);
                        }
                    }
            );
        }
    }
}
