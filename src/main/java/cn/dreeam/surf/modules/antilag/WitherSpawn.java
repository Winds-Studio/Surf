package cn.dreeam.surf.modules.antilag;

import cn.dreeam.surf.config.ConfigCache;
import cn.dreeam.surf.util.Utils;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class WitherSpawn implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onWitherSpawn(EntitySpawnEvent event) {
        if (Utils.getTps() <= ConfigCache.LimitWitherSpawnOnLagDisableTPS && event.getEntity() instanceof Wither) {
            event.setCancelled(true);
        }
    }
}