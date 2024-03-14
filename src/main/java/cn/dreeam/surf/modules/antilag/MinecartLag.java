package cn.dreeam.surf.modules.antilag;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Util;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.Arrays;

public class MinecartLag implements Listener {

    private final int max = Surf.config.limitVehicleMinecartPerChunkLimit();

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(VehicleCreateEvent event) {
        if (!Surf.config.limitVehicleEnabled()) return;

        if (Util.getTps() <= Surf.config.limitVehicleDisableTPS()) {
            Vehicle vehicle = event.getVehicle();
            Chunk chunk = vehicle.getChunk();
            Player player = Util.getNearbyPlayer(20, vehicle.getLocation());
            String formattedName = vehicle.getType().toString().toLowerCase().concat("s");
            long amount = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof Vehicle).count();

            if (amount >= max) {
                event.setCancelled(true);
                for (Entity ent : event.getVehicle().getChunk().getEntities()) {
                    if (ent instanceof Vehicle) {
                        ent.remove();
                    }
                }
                Util.sendMessage(player, Util.getPrefix() + "&6Please limit " + formattedName + " to &r&c" + max + "&r&6 per chunk");
                Util.println(Util.getPrefix() + "&6Removed &r&3" + chunk.getEntities().length + " " + formattedName + "&r&6 from a lag machine owned by&r&3 " + player.getName());
            }
        }
    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (!Surf.config.limitVehicleEnabled()) return;

        if (Util.getTps() <= Surf.config.limitVehicleDisableTPS()) {
            Vehicle vehicle = event.getVehicle();
            Chunk chunk = vehicle.getChunk();
            String formattedName = vehicle.getType().toString().toLowerCase().concat("s").replace("_", " ");
            String formattedName1 = vehicle.getType().toString().toLowerCase().replace("_", " ");
            Player player = Util.getNearbyPlayer(20, vehicle.getLocation());

            if (!event.getFrom().getChunk().equals(event.getTo().getChunk())) {
                if (chunk.getEntities().length >= Surf.config.limitVehicleMinecartPerChunkLimit()) {
                    vehicle.remove();
                    Util.sendMessage(player, Util.getPrefix() + "&6Please limit " + formattedName + " to &r&c" + max + "&r&6 per chunk");
                    Util.println(Util.getPrefix() + "&6Deleted a &r&3" + formattedName1 + "&r&6 from a lag machine owned by&r&3 " + player.getName() + " &4BYPASS ATTEMPT");
                }
            }
        }
    }
}
