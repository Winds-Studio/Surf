package cn.dreeam.surf.modules;

import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (ConfigCache.ConnectionEnabled) {
            event.joinMessage(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(ConfigCache.ConnectionPlayerJoinMessage.replace("%player%", event.getPlayer().getDisplayName())));
        } else if (ConfigCache.FirstJoinEnabled && !player.hasPlayedBefore()) {
            Bukkit.getServer().broadcast(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(ConfigCache.FirstJoinMessage.replace("%Player%", player.getName())));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (ConfigCache.ConnectionEnabled) {
            event.quitMessage(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(ConfigCache.ConnectionPlayerLeaveMessage.replace("%player%", event.getPlayer().getDisplayName())));
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (ConfigCache.ConnectionPreventKickEnabled) {
            ConfigCache.ConnectionKickReasons.forEach(reason -> {
                if (event.getReason().equalsIgnoreCase(reason)) {
                    event.setCancelled(true);
                    Utils.println(Utils.getPrefix() + "Canceled a kick, Reason: " + reason);
                }
            });
        }
    }
}