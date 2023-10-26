package org.surf.modules;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.surf.util.ConfigCache;

public class ConnectionEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (ConfigCache.ConnectionEnabled) {
            event.joinMessage(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(ConfigCache.ConnectionPlayerJoinMessage.replace("%player%", event.getPlayer().getDisplayName())));
        }
        else if (ConfigCache.FirstJoinEnabled && !player.hasPlayedBefore()) {
            Bukkit.getServer().broadcast(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(ConfigCache.FirstJoinMessage.replace("%Player%", player.getName())));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!player.getActivePotionEffects().isEmpty()) {
            for (PotionEffect effects : player.getActivePotionEffects()) {
                if (effects.getAmplifier() > 5 || effects.getDuration() > 12000) {
                    player.removePotionEffect(effects.getType());
                }
            }
        }
        else if (ConfigCache.ConnectionEnabled) {
            event.quitMessage(
                    LegacyComponentSerializer.legacyAmpersand().deserialize(ConfigCache.ConnectionPlayerLeaveMessage.replace("%player%", event.getPlayer().getDisplayName())));
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (event.getReason().equalsIgnoreCase("Kicked for spamming")) {
            event.setCancelled(true);
        }
        if (event.getReason().equalsIgnoreCase("Invalid hotbar selection (Hacking?)")) {
            event.setCancelled(true);
        }
    }
}