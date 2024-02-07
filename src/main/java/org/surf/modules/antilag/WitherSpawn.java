package org.surf.modules.antilag;

import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.surf.util.Utils;

public class WitherSpawn implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onWitherSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Wither && Utils.getTps() <= 16) {
            event.setCancelled(true);
        }
    }
}