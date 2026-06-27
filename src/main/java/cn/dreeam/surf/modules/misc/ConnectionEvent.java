package cn.dreeam.surf.modules.misc;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
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
        if (!Config.Misc.connectionMessageEnabled) return;

        Player player = event.getPlayer();
        String message = Config.Misc.connectionFirstJoinEnabled && !player.hasPlayedBefore()
                ? getConnectionMessageLegacy(player, Config.Misc.connectionFirstJoinMessage)
                : getConnectionMessageLegacy(player, Config.Misc.connectionPlayerJoin);

        event.setJoinMessage(message);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Config.Misc.connectionMessageEnabled) return;

        String message = getConnectionMessageLegacy(event.getPlayer(), Config.Misc.connectionPlayerLeave);

        event.setQuitMessage(message);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (!Config.Misc.connectionPreventKickEnabled) return;

        String reason = event.getReason();

        if (Config.Misc.connectionKickReasons.contains(reason)) {
            event.setCancelled(true);
            MessageUtil.println("Cancelled a kick for " + event.getPlayer().getName() + ", Reason: " + reason);
        }
    }

    private String getConnectionMessageLegacy(Player player, String message) {
        String displayName = player.getDisplayName();
        message = ChatColor.translateAlternateColorCodes('&', message);

        if (Config.Misc.connectionMessageUseDisplayName && !displayName.isEmpty()) {
            message = message.replace("%player%", displayName);
        } else {
            message = message.replace("%player%", player.getName());
        }

        return message;
    }
}
