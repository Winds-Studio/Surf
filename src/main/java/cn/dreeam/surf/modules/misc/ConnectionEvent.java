package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
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
        if (!Surf.config.connectionMessageEnabled()) return;

        Player player = event.getPlayer();
        event.setJoinMessage(null);
        Surf.getInstance().adventure().all().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                ((Surf.config.connectionFirstJoinEnabled() && !player.hasPlayedBefore()) ? Surf.config.connectionFirstJoinMessage() : Surf.config.connectionPlayerJoin())
                        .replace("%Player%", player.getDisplayName())));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Surf.config.connectionMessageEnabled()) return;

        event.setQuitMessage(null);
        Surf.getInstance().adventure().all().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                Surf.config.connectionPlayerLeave().replace("%player%", event.getPlayer().getDisplayName())));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (!Surf.config.connectionPreventKickEnabled()) return;

        Surf.config.connectionKickReasons().forEach(reason -> {
            if (event.getReason().equalsIgnoreCase(reason)) {
                event.setCancelled(true);
                Util.println(event.getPlayer(), Util.getPrefix() + "Canceled a kick, Reason: " + reason);
            }
        });
    }
}