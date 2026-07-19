package cn.dreeam.surf.modules.misc.antilag;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
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

    private final int max = Config.AntiLag.limitVehicleMinecartPerChunkLimit;

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(VehicleCreateEvent event) {
        if (!Config.AntiLag.limitVehicleEnabled) return;

        if (Util.getTps() <= Config.AntiLag.limitVehicleDisableTPS) {
            Vehicle vehicle = event.getVehicle();
            Chunk chunk = vehicle.getChunk();
            Player player = Util.getNearestPlayer(20, vehicle.getLocation());
            String formattedName = vehicle.getType().toString().toLowerCase().concat("s");
            long amount = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof Vehicle).count();

            if (amount >= max) {
                event.setCancelled(true);

                for (Entity ent : event.getVehicle().getChunk().getEntities()) {
                    if (ent instanceof Vehicle) {
                        ent.remove();
                    }
                }

                MessageUtil.sendMessage(player, Util.getPrefix() + "&6Please limit " + formattedName + " to &r&c" + max + "&r&6 per chunk");
            }
        }
    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (!Config.AntiLag.limitVehicleEnabled) return;

        if (Util.getTps() <= Config.AntiLag.limitVehicleDisableTPS) {
            Vehicle vehicle = event.getVehicle();
            Chunk chunk = vehicle.getChunk();
            String formattedName = vehicle.getType().toString().toLowerCase().concat("s").replace("_", " ");
            Player player = Util.getNearestPlayer(20, vehicle.getLocation());

            if (!event.getFrom().getChunk().equals(event.getTo().getChunk())) {
                if (chunk.getEntities().length >= Config.AntiLag.limitVehicleMinecartPerChunkLimit) {
                    vehicle.remove();
                    MessageUtil.sendMessage(player, Util.getPrefix() + "&6Please limit " + formattedName + " to &r&c" + max + "&r&6 per chunk");
                }
            }
        }
    }
}
