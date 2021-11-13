package org.surf.listeners.patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.surf.Main;

public class dispensor implements Listener {
    Main plugin;
    public dispensor(Main plugin) { this.plugin = plugin; }

    @EventHandler
    public void dispense (BlockDispenseEvent event) {
        if (event.getBlock().getY() > 254) {
            event.setCancelled(true);
        }
        if (event.getBlock().getY() < 2) {
            event.setCancelled(true);
        }
    }
}
