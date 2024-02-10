package org.surf.modules.patches;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class IllegalDamageAndPotionCheck implements Listener {

    // Player get damage
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }
        if (event.getDamage() > 30) {
            event.setCancelled(true);
            Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
        }

        /*
        Entity damager = event.getDamager();
        if (event.getEntity().getEntityId() != damager.getEntityId() && event.getEntity() instanceof Player) {
            // Cancel damage if an entity get damage >= 30
            if (event.getDamage() > 30) {
                event.setCancelled(true);
                // Send msg if damager is a player
                if (damager instanceof Player) Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
            }
        }
         */
    }

    // Arrow shoot by player
    @EventHandler(ignoreCancelled = true)
    public void onBow(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow)) {
            return;
        }
        if (((Arrow) event.getDamager()).getShooter() instanceof Player damager && event.getDamage() > 40) {
            event.setCancelled(true);
            Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
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
    }

    // Check Player throw Potion with illegal potion effects
    @EventHandler(ignoreCancelled = true)
    public void onPotion(EntityPotionEffectEvent event) {
        PotionEffect effect = event.getNewEffect();
        if (effect != null &&
                (effect.getAmplifier() > 5 || effect.getDuration() > 12000)) {
            event.setCancelled(true);
            if (event.getEntity() instanceof Player player) Utils.sendMessage(player, ConfigCache.IllegalPotionMessage);
        }
    }

    // Check Potion/Arrow/Trident with illegal potion effects dispense from dispenser
    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        String material = event.getItem().getType().name();

        // Needs to add more items if they are added in newer MC version,
        // or remove material check
        if (material.contains("POTION")|| material.contains("ARROW") || material.contains("TRIDENT")) {
            Dispenser disp = (Dispenser) event.getBlock().getState();
            PotionMeta pot = (PotionMeta) event.getItem().getItemMeta();

            for (PotionEffect effects : pot.getCustomEffects()) {
                if (effects.getAmplifier() > 5
                        || effects.getDuration() > 12000) {
                    event.setCancelled(true);
                    disp.getInventory().remove(event.getItem());
                    Utils.println(ConfigCache.IllegalPotionMessage);
                    // One illegal potion effect appear, remove whole item
                    // then break the for loop.
                    break;
                }
            }
        }
    }
}
