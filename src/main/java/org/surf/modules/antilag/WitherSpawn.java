package org.surf.modules.antilag;

import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class WitherSpawn implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onWitherSpawn(EntitySpawnEvent event) {
        if (Utils.getTps() <= ConfigCache.LimitWitherSpawnOnLagDisableTPS && event.getEntity() instanceof Wither) {
            event.setCancelled(true);
        }
    }
}