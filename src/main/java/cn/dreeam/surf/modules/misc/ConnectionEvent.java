package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Utils;
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
        event.joinMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                ((Surf.config.connectionFirstJoinEnabled() && !player.hasPlayedBefore()) ? Surf.config.connectionFirstJoinMessage() : Surf.config.connectionPlayerJoin())
                        .replace("%Player%", player.getDisplayName())));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Surf.config.connectionMessageEnabled()) return;

        event.quitMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                Surf.config.connectionPlayerLeave().replace("%player%", event.getPlayer().getDisplayName())));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (Surf.config.connectionPreventKickEnabled()) return;

        Surf.config.connectionKickReasons().forEach(reason -> {
            if (event.getReason().equalsIgnoreCase(reason)) {
                event.setCancelled(true);
                Utils.println(event.getPlayer(), Utils.getPrefix() + "Canceled a kick, Reason: " + reason);
            }
        });
    }
}