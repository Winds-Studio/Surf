package org.surf.modules.patches;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class IllegalDamageAndPotionCheck implements Listener {

    // Entity gets damage
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        // Player => Entity
        if (event.getDamager() instanceof Player damager) {
            if (event.getDamage() > 30) {
                event.setCancelled(true);
                Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
            }
        } else {
            // Entity => Entity
            Entity entity = event.getDamager();

            if (entity instanceof LivingEntity damager) {
                // Only check entities using illegal items
                if (damager.getEquipment() != null && damager.getEquipment().getItemInMainHand().hasItemMeta()) {
                    if (event.getDamage() > 30) {
                        event.setCancelled(true);
                        Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
                    }
                }
            }
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

    // Check Entity gets illegal potion effects
    @EventHandler(ignoreCancelled = true)
    public void onPotion(EntityPotionEffectEvent event) {
        PotionEffect effect = event.getNewEffect();
        if (effect != null &&
                (effect.getAmplifier() > 5 || effect.getDuration() > 12000)) {
            event.setCancelled(true);
            if (event.getEntity() instanceof Player player) Utils.sendMessage(player, ConfigCache.IllegalPotionMessage);
        }
    }
}
