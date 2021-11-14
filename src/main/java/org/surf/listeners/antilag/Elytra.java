package org.surf.listeners.antilag;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.surf.Main;
import org.surf.util.Utils;

public class Elytra implements Listener {
    Main plugin;

    public Elytra(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void NoElytraFallDamage(EntityDamageEvent event) {
        if (plugin.getConfigBoolean("Elytra.EnableNoFallDamage")) {
            if (event.getCause() == null) return;
            if (event.getEntity() == null) return;
            if (!(event.getEntity() instanceof Player)) return;
            Player player = (Player) event.getEntity();
            if (player.getInventory().getChestplate() == null) return;
            Material chestPlate = player.getInventory().getChestplate().getType();
            if (chestPlate == Material.ELYTRA) {
                event.setCancelled(event.getCause() == EntityDamageEvent.DamageCause.FALL);
            }
        }
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        try {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (Utils.getTps() <= plugin.getConfig().getInt("Elytra.Disable-TPS")) {
                    event.setCancelled(true);
                    Utils.sendMessage(player, plugin.getConfig().getString("ElytraLowTPS.Message").replace("{tps}", "" + plugin.getConfig().getInt("Elytra.Disable-TPS")));
                }
            }
        } catch (Error | Exception throwable) {
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        try {
            if (Utils.getTps() <= plugin.getConfig().getInt("Elytra.Disable-TPS")) {
                if (event.getPlayer().isGliding()) {
                    event.getPlayer().setGliding(false);
                    Utils.sendMessage(event.getPlayer(), plugin.getConfig().getString("ElytraLowTPS.Message").replace("{tps}", "" + plugin.getConfig().getInt("Elytra.Disable-TPS")));
                }
            }
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();
            double distX = to.getX() - from.getX();
            double distZ = to.getZ() - from.getZ();
            double finalValue = Math.round(Math.hypot(distX, distZ));
            if (finalValue > plugin.getConfig().getInt("Elytra.SpeedLimit")) {
                event.setCancelled(true);
                if (player.isGliding()) {
                   if (plugin.getConfigBoolean("Elytra.EnableTakingOffElytra")) {
                       if (player.getInventory().getChestplate().getType() == Material.ELYTRA) {
                           player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
                           player.getInventory().setChestplate(null);
                           if (plugin.getConfigBoolean("Elytra.EnableOpMessages")) {
                               if (plugin.getConfigBoolean("Elytra.EnableDamage")) {
                                   Utils.sendOpMessage(plugin.getConfig().getString("Elytra.SpeeedLimitReached-ops").replace("{name}", "" + player.getName()));
                                   player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                                   player.setGliding(false);
                                   player.damage(plugin.getConfig().getInt("Elytra.DamageAmount"));
                               } else {
                                   Utils.sendOpMessage(plugin.getConfig().getString("Elytra.SpeeedLimitReached-ops").replace("{name}", "" + player.getName()));
                                   player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                                   player.setGliding(false);
                               }
                           } else {
                               if (plugin.getConfigBoolean("Elytra.EnableDamage")) {
                                   player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                                   player.setGliding(false);
                                   player.damage(plugin.getConfig().getInt("Elytra.DamageAmount"));
                               } else {
                                   player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                                   player.setGliding(false);
                               }
                           }
                       }
                   } else {
                       if (plugin.getConfigBoolean("Elytra.EnableOpMessages")) {
                           if (plugin.getConfigBoolean("Elytra.EnableDamage")) {
                               Utils.sendOpMessage(plugin.getConfig().getString("Elytra.SpeeedLimitReached-ops").replace("{name}", "" + player.getName()));
                               player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                               player.setGliding(false);
                               player.damage(plugin.getConfig().getInt("Elytra.DamageAmount"));
                           } else {
                               Utils.sendOpMessage(plugin.getConfig().getString("Elytra.SpeeedLimitReached-ops").replace("{name}", "" + player.getName()));
                               player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                               player.setGliding(false);
                           }
                       } else {
                           if (plugin.getConfigBoolean("Elytra.EnableDamage")) {
                               player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                               player.setGliding(false);
                               player.damage(plugin.getConfig().getInt("Elytra.DamageAmount"));
                           } else {
                               player.sendMessage(plugin.getConfig().getString("Elytra.SpeedLimitReached-message"));
                               player.setGliding(false);
                           }
                       }
                   }
                }
            }
        } catch (Error | Exception throwable) {

        }
    }
}