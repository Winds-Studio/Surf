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
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class GateWay implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCrashAttempt(EntityTeleportEndGatewayEvent event) {
        if (!ConfigCache.GateWayPreventCrashExploit) {
            return;
        }
        double randomX = (Math.random() * ((50) + 1)) + 0;
        double randomY = (Math.random() * ((50) + 1)) + 0;
        double randomZ = (Math.random() * ((50) + 1)) + 0;
        int x = event.getFrom().getBlockX();
        int y = event.getGateway().getLocation().getBlockY();
        int z = event.getFrom().getBlockZ();
        Vector vector = new Vector(-randomX, randomY, randomZ);
        Entity entity = event.getEntity();
        if (!(entity instanceof Vehicle)) {
            return;
        }
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

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        if (!ConfigCache.GateWayPreventEntityEnterPortal) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof Item) {
            event.setCancelled(true);
            return;
        }
        if (entity instanceof ChestedHorse) {
            entity.eject();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EndGatewayTeleportProtection(VehicleMoveEvent event) {
        if (!ConfigCache.GateWayPreventEntityEnterPortal) {
            return;
        }
        Vehicle vehicle = event.getVehicle();
        if (vehicle.getWorld().getEnvironment() != Environment.THE_END) {
            return;
        }
        if (!(vehicle.getPassenger() instanceof Player)) {
            return;
        }
        Player player = (Player) vehicle.getPassenger();
        for (BlockFace face : BlockFace.values()) {
            Block next = vehicle.getLocation().getBlock().getRelative(face);
            if (next.getType() == Material.END_GATEWAY) {
                int x = vehicle.getLocation().getBlockX();
                int y = vehicle.getLocation().getBlockY();
                int z = vehicle.getLocation().getBlockZ();
                String worldString = vehicle.getWorld().getName();
                vehicle.eject();
                vehicle.remove();
                Utils.kickPlayer(player, "[&b&lSurf&r]&6 Sorry that exploit got patched :(");
                System.out.println(ChatColor.translateAlternateColorCodes('&', "&1Prevented&r&e " + player.getName() + "&r&1 at &r&e" + x + " " + y + " " + z + " &r&1in world&e " + worldString + " &r&1from crashing the server"));
            }
        }
    }
}