package org.surf.modules.patches;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.surf.Main;

public class Dispensor implements Listener {
    Main plugin;

    public Dispensor(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void dispense(BlockDispenseEvent event) {
        String bVersion = Bukkit.getVersion();

        if (bVersion.contains("1.12")) {
            if (event.getBlock().getY() > 254 || event.getBlock().getY() < 2) {
                event.setCancelled(true);
            }
        }
    }
}
