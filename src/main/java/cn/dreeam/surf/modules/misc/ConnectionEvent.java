package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.Util;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Config.connectionMessageEnabled) return;

        Player player = event.getPlayer();
        event.setJoinMessage(null);
        Surf.getInstance().adventure().all().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                ((Config.connectionFirstJoinEnabled && !player.hasPlayedBefore()) ? Config.connectionFirstJoinMessage : Config.connectionPlayerJoin)
                        .replace("%Player%", player.getDisplayName())));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Config.connectionMessageEnabled) return;

        event.setQuitMessage(null);
        Surf.getInstance().adventure().all().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                Config.connectionPlayerLeave.replace("%player%", event.getPlayer().getDisplayName())));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (!Config.connectionPreventKickEnabled) return;

        Config.connectionKickReasons.forEach(reason -> {
            if (event.getReason().equalsIgnoreCase(reason)) {
                event.setCancelled(true);
                Util.println("Cancelled a kick for " + event.getPlayer().getName() + ", Reason: " + reason);
            }
        });
    }
}