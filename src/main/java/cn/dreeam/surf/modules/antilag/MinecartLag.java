package cn.dreeam.surf.modules.antilag;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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

    public MinecartLag() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(VehicleCreateEvent event) {
        if (Utils.getTps() <= ConfigCache.LimitVehicleDisableTPS) {
            Chunk chunk = event.getVehicle().getChunk();
            Vehicle vehicle = event.getVehicle();
            Player player = Utils.getNearbyPlayer(20, vehicle.getLocation());
            String formattedName = vehicle.getType().toString().toLowerCase().concat("s");
            long amount = Arrays.stream(chunk.getEntities()).filter(entity -> entity instanceof Vehicle).count();

            if (amount >= ConfigCache.MinecartPerChunkLimit) {
                event.setCancelled(true);
                Utils.sendMessage(player, Utils.getPrefix() + "&6Please limit " + formattedName + " to &r&c" + ConfigCache.MinecartPerChunkLimit + "&r&6 per chunk");
                Utils.sendOpMessage(Utils.getPrefix() + "&6Removed &r&3" + chunk.getEntities().length + " " + formattedName + "&r&6 from a lag machine owned by&r&3 " + player.getName());
                System.out.println(LegacyComponentSerializer.legacyAmpersand().deserialize(Utils.getPrefix() + "&6Removed &r&3" + chunk.getEntities().length + " " + formattedName + "&r&6 from a lag machine owned by&r&3 " + player.getName()));

                for (Entity ent : event.getVehicle().getChunk().getEntities()) {
                    if (ent instanceof Vehicle) {
                        ent.remove();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (Utils.getTps() <= ConfigCache.LimitVehicleDisableTPS) {
            Chunk chunk = event.getVehicle().getChunk();
            Vehicle vehicle = event.getVehicle();
            String formattedName = vehicle.getType().toString().toLowerCase().concat("s").replace("_", " ");
            String formattedName1 = vehicle.getType().toString().toLowerCase().replace("_", " ");
            int max = ConfigCache.MinecartPerChunkLimit;
            Player player = Utils.getNearbyPlayer(20, vehicle.getLocation());

            if (!event.getFrom().getChunk().equals(event.getTo().getChunk())) {
                if (chunk.getEntities().length >= max) {
                    vehicle.remove();
                    Utils.sendMessage(player, Utils.getPrefix() + "&6Please limit " + formattedName + " to &r&c" + max + "&r&6 per chunk");
                    Utils.sendOpMessage(Utils.getPrefix() + "&6Deleted a &r&3" + formattedName1 + "&r&6 from a lag machine owned by&r&3 " + player.getName() + " &4BYPASS ATTEMPT");
                    Surf.LOGGER.info("{}&6Deleted a &r&3{}&r&6 from a lag machine owned by&r&3 {} &4BYPASS ATTEMPT", Utils.getPrefix(), formattedName1, player.getName());
                }
            }
        }
    }
}
