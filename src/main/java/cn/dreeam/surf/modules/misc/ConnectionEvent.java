package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

        if (Util.isNewerAndEqual(16, 0)) {
            Component message = Config.connectionFirstJoinEnabled && !player.hasPlayedBefore()
                    ? getConnectionMessageModern(player, Config.connectionFirstJoinMessage)
                    : getConnectionMessageModern(player, Config.connectionPlayerJoin);

            event.setJoinMessage(null);
            broadcastConnectionMessage(message);
        } else {
            String message = Config.connectionFirstJoinEnabled && !player.hasPlayedBefore()
                    ? getConnectionMessageLegacy(player, Config.connectionFirstJoinMessage)
                    : getConnectionMessageLegacy(player, Config.connectionPlayerJoin);

            event.setJoinMessage(message);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Config.connectionMessageEnabled) return;

        if (Util.isNewerAndEqual(16, 0)) {
            Component message = getConnectionMessageModern(event.getPlayer(), Config.connectionPlayerLeave);

            event.setQuitMessage(null);
            broadcastConnectionMessage(message);
        } else {
            String message = getConnectionMessageLegacy(event.getPlayer(), Config.connectionPlayerLeave);

            event.setQuitMessage(message);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (!Config.connectionPreventKickEnabled) return;

        String reason = event.getReason();

        if (Config.connectionKickReasons.contains(reason)) {
            event.setCancelled(true);
            MessageUtil.println("Cancelled a kick for " + event.getPlayer().getName() + ", Reason: " + reason);
        }
    }

    private Component getConnectionMessageModern(Player player, String message) {
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

    private String getConnectionMessageLegacy(Player player, String message) {
        String displayName = player.getDisplayName();
        message = ChatColor.translateAlternateColorCodes('&', message);

        if (Config.connectionMessageUseDisplayName && !displayName.isEmpty()) {
            message = message.replace("%player%", displayName);
        } else {
            message = message.replace("%player%", player.getName());
        }

        return message;
    }

    private static void broadcastConnectionMessage(Component message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null) continue;

            player.sendMessage(message);
        }
    }
}
