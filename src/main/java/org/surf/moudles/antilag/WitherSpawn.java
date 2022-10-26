package org.surf.moudles.antilag;

import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.surf.util.Utils;

public class WitherSpawn implements Listener {
	@EventHandler
	public void onWitherSpawn(EntitySpawnEvent event) {
		try {
			if (event.getEntity() instanceof Wither) {
				if (Utils.getTps() <= 16) {
					event.setCancelled(true);

				}
			}
		} catch (Error | Exception throwable) {

		}
	}
}