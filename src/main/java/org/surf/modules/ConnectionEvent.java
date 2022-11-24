package org.surf.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        event.setJoinMessage(
                ChatColor.translateAlternateColorCodes('&', ConfigCache.ConnectionPlayerJoinMessage.replace("%player%", event.getPlayer().getDisplayName())));
        if (ConfigCache.FirstJoinEnabled && !player.hasPlayedBefore()) {
            Bukkit.getServer().broadcastMessage(
                    ChatColor.translateAlternateColorCodes('&', ConfigCache.FirstJoinMessage.replace("%Player%", player.getName())));
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
        event.setQuitMessage(
                ChatColor.translateAlternateColorCodes('&', ConfigCache.ConnectionPlayerLeaveMessage.replace("%player%", event.getPlayer().getDisplayName())));
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