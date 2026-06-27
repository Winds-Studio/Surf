package cn.dreeam.surf.modules.misc.antilag;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.Util;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class WitherSpawn implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onWitherSpawn(EntitySpawnEvent event) {
        if (!Config.AntiLag.limitWitherSpawnOnLagEnabled) return;

        if (Util.getTps() <= Config.AntiLag.limitWitherSpawnOnLagDisableTPS && event.getEntity() instanceof Wither) {
            event.setCancelled(true);
        }
    }
}