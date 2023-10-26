package org.surf.modules.antilag;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.surf.Main;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

import java.util.Arrays;

public class MinecartLag implements Listener {
    private final Main plugin;

    public MinecartLag(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpawn(VehicleCreateEvent event) {
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

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
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
                plugin.getLogger().info(Utils.getPrefix() + "&6Deleted a &r&3" + formattedName1 + "&r&6 from a lag machine owned by&r&3 " + player.getName() + " &4BYPASS ATTEMPT");
            }
        }
    }
}
