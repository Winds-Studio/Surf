package cn.dreeam.surf.modules.patch;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.Util;
import com.cryptomorin.xseries.XMaterial;
import com.destroystokyo.paper.event.entity.EntityTeleportEndGatewayEvent;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class GateWay implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCrashAttempt(EntityTeleportEndGatewayEvent event) {
        if (!Config.gateWayPreventCrashExploit) return;

        double randomX = (Math.random() * ((50) + 1)) + 0;
        double randomY = (Math.random() * ((50) + 1)) + 0;
        double randomZ = (Math.random() * ((50) + 1)) + 0;
        Vector vector = new Vector(-randomX, randomY, randomZ);
        Entity entity = event.getEntity();

        if (!(entity instanceof Vehicle)) return;

        for (Player nearby : entity.getLocation().getNearbyPlayers(30)) {
            nearby.teleport(new Location(nearby.getWorld(), nearby.getLocation().getBlockX(),
                    nearby.getLocation().getBlockY() + 5, nearby.getLocation().getBlockZ() + 30));
            entity.setVelocity(vector);
            event.setCancelled(true);
            MessageUtil.sendMessage(nearby, "Going through ENDGATEWAY while riding " + entity.getName() + " is currently patched");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityPortal(EntityPortalEvent event) {
        if (!Config.gateWayPreventEntityEnterPortal) return;

        Entity entity = event.getEntity();

        if (entity instanceof Item) {
            event.setCancelled(true);
            return;
        }

        if (entity instanceof ChestedHorse) {
            entity.eject();
            event.setCancelled(true);

            MessageUtil.println(String.format(
                    "&1Prevented a entity enter portal crash at %s",
                    MessageUtil.locToString(entity.getLocation())
            ));
        }
    }

    @EventHandler
    public void EndGatewayTeleportProtection(VehicleMoveEvent event) {
        if (!Config.gateWayPreventEntityEnterPortal) return;

        Vehicle vehicle = event.getVehicle();

        if (vehicle.getWorld().getEnvironment() != Environment.THE_END || !(vehicle.getPassenger() instanceof Player)) return;

        Player player = (Player) vehicle.getPassenger();
        for (BlockFace face : BlockFace.values()) {
            Block next = vehicle.getLocation().getBlock().getRelative(face);
            if (next.getType().equals(XMaterial.END_GATEWAY.parseMaterial())) {
                vehicle.eject();
                vehicle.remove();
                Util.kickPlayer(player, "Sorry this exploit got patched :(");

                MessageUtil.println(String.format(
                        "&1Prevented&r&e %s &r&1 at &r&e %s &r&1from crashing the server",
                        player.getName(),
                        MessageUtil.locToString(vehicle.getLocation())
                ));
            }
        }
    }
}