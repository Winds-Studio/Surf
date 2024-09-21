package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
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
        Component message = Config.connectionFirstJoinEnabled && !player.hasPlayedBefore()
                ? getConnectionMessage(player, Config.connectionFirstJoinMessage)
                : getConnectionMessage(player, Config.connectionPlayerJoin);

        event.setJoinMessage(null);
        Surf.getInstance().adventure().all().sendMessage(message);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Config.connectionMessageEnabled) return;

        Component message = getConnectionMessage(event.getPlayer(), Config.connectionPlayerLeave);

        event.setQuitMessage(null);
        Surf.getInstance().adventure().all().sendMessage(message);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (!Config.connectionPreventKickEnabled) return;

        String reason = event.getReason();

        if (Config.connectionKickReasons.contains(reason)) {
            event.setCancelled(true);
            Util.println("Cancelled a kick for " + event.getPlayer().getName() + ", Reason: " + reason);
        }
    }

    private Component getConnectionMessage(Player player, String message) {
        Component connecntionComponent;

        if (Config.connectionMessageUseDisplayName && !player.displayName().equals(Component.empty())) {
            connecntionComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(message)
                    .replaceText(TextReplacementConfig.builder().matchLiteral("%player%").replacement(player.displayName()).build());
        } else {
            message = message.replace("%player%", player.getName());
            connecntionComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        }

        return connecntionComponent;
    }
}