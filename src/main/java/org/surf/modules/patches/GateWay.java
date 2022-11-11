package org.surf.modules.patches;

import com.destroystokyo.paper.event.entity.EntityTeleportEndGatewayEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;
import org.surf.Main;
import org.surf.util.Utils;

public class GateWay implements Listener {
    private final Main plugin;

    public GateWay(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCrashAttempt(EntityTeleportEndGatewayEvent event) {
        if (plugin.getConfig().getBoolean("GateWay.PreventCrashExploit")) {
            try {
                double randomX = (Math.random() * ((50) + 1)) + 0;
                double randomY = (Math.random() * ((50) + 1)) + 0;
                double randomZ = (Math.random() * ((50) + 1)) + 0;
                int x = event.getFrom().getBlockX();
                int y = event.getGateway().getLocation().getBlockY();
                int z = event.getFrom().getBlockZ();
                Vector vector = new Vector(-randomX, randomY, randomZ);
                Entity entity = event.getEntity();
                if (entity instanceof Boat || entity instanceof Minecart || entity instanceof Pig || entity instanceof Mule
                        || entity instanceof Horse) {
                    for (Player nearby : entity.getLocation().getNearbyPlayers(30)) {
                        nearby.sendMessage(ChatColor.GOLD
                                + "Going through ENDGATEWAY while riding "
                                + entity.getName()
                                + " is currently patched");
                        nearby.teleport(new Location(nearby.getWorld(), nearby.getLocation().getBlockX(),
                                nearby.getLocation().getBlockY() + 5, nearby.getLocation().getBlockZ() + 30));
                        entity.setVelocity(vector);
                        event.setCancelled(true);
                        System.out.println(ChatColor.translateAlternateColorCodes('&',
                                "&1Prevented&r&e " + nearby.getName() + "&r&1 at &r&e" + x + " " + y + " " + z
                                        + " &r&1in world&e " + entity.getWorld().getName() + " &r&1from crashing the server"));
                    }
                }
            } catch (Error | Exception throwable) {

            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        if (plugin.getConfig().getBoolean("GateWay.PreventEntityEnterPortal")) {
            try {
                Entity entity = event.getEntity();
                if (entity.getPassenger() instanceof Player) {
                    Player player = (Player) event.getEntity().getPassenger();
                    if (entity instanceof Item || entity instanceof Donkey || entity instanceof Llama) {
                        player.getVehicle().eject();
                        event.setCancelled(true);
                        entity.remove();
                    }
                }
            } catch (Error | Exception throwable) {

            }
        }
    }

    @EventHandler
    public void EndGatewayTeleportProtection(VehicleMoveEvent event) {
        if (plugin.getConfig().getBoolean("GateWay.PreventEntityEnterPortal")) {
            try {
                if (event.getVehicle().getWorld().getEnvironment() == Environment.THE_END) {
                    if (event.getVehicle().getPassenger() instanceof Player) {
                        Player player = (Player) event.getVehicle().getPassenger();
                        for (BlockFace face : BlockFace.values()) {
                            Block next = event.getVehicle().getLocation().getBlock().getRelative(face);
                            if (next.getType() == Material.END_GATEWAY) {
                                int x = event.getVehicle().getLocation().getBlockX();
                                int y = event.getVehicle().getLocation().getBlockY();
                                int z = event.getVehicle().getLocation().getBlockZ();
                                String worldString = event.getVehicle().getWorld().getName();
                                event.getVehicle().eject();
                                event.getVehicle().remove();
                                Utils.kickPlayer(player, "[&b&lSurf&r]&6 Sorry that exploit got patched :(");
                                System.out.println(ChatColor.translateAlternateColorCodes('&', "&1Prevented&r&e " + player.getName() + "&r&1 at &r&e" + x + " " + y + " " + z + " &r&1in world&e " + worldString + " &r&1from crashing the server"));
                            }
                        }
                    }
                }
            } catch (Error | Exception throwable) {

            }
        }
    }
}