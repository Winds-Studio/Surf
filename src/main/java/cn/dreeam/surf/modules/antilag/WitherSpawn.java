package cn.dreeam.surf.modules.antilag;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.Util;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class WitherSpawn implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onWitherSpawn(EntitySpawnEvent event) {
        if (!Config.limitWitherSpawnOnLagEnabled) return;

        if (Util.getTps() <= Config.limitWitherSpawnOnLagDisableTPS && event.getEntity() instanceof Wither) {
            event.setCancelled(true);
        }
    }
}