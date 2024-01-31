package org.surf.modules.patches;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.surf.util.ConfigCache;
import org.surf.util.Utils;

public class IllegalDamageAndPotionCheck implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!ConfigCache.AntiIllegalCheckIllegalDamage) {
            return;
        }
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

    @EventHandler(ignoreCancelled = true)
    public void onBow(EntityDamageByEntityEvent event) {
        if (!ConfigCache.AntiIllegalCheckIllegalDamage) {
            return;
        }
        if (!(event.getDamager() instanceof Arrow)) {
            return;
        }
        if (((Arrow) event.getDamager()).getShooter() instanceof Player damager && event.getDamage() > 40) {
            event.setCancelled(true);
            Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onThrow(PotionSplashEvent event) {
        if (!(event.getPotion().getShooter() instanceof Player player)) {
            return;
        }
        ItemStack pot = event.getPotion().getItem();
        for (PotionEffect effects : event.getPotion().getEffects()) {
            if (effects.getAmplifier() > 5
                    || effects.getDuration() > 12000) {
                event.setCancelled(true);
                player.getInventory().remove(pot);
                Utils.sendMessage(player, ConfigCache.IllegalPotionMessage);
                break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void PlayerInteractEvent(PlayerItemConsumeEvent e) {
        if (!e.getItem().getType().equals(Material.POTION)) {
            return;
        }
        if (!e.getItem().hasItemMeta()) {
            return;
        }
        PotionMeta potion = (PotionMeta) e.getItem().getItemMeta();
        for (PotionEffect pe : potion.getCustomEffects()) {
            if (pe.getAmplifier() > 5
                    || pe.getDuration() > 12000) {
                e.setCancelled(true);
                e.getPlayer().getInventory().remove(e.getItem());
                Utils.sendMessage(e.getPlayer(), ConfigCache.IllegalPotionMessage);
                break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        String material = event.getItem().getType().name();

        if (material.contains("POTION")|| material.contains("ARROW") || material.contains("TRIDENT")) {
            Dispenser disp = (Dispenser) event.getBlock().getState();
            PotionMeta pot = (PotionMeta) event.getItem().getItemMeta();

            for (PotionEffect effects : pot.getCustomEffects()) {
                if (effects.getAmplifier() > 5
                        || effects.getDuration() > 12000) {
                    event.setCancelled(true);
                    disp.getInventory().remove(event.getItem());
                    // One illegal potion effect appear, remove whole item
                    // then break the for loop.
                    break;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow) || !(event.getEntity().getShooter() instanceof Player)
                || !(event.getHitEntity() instanceof Player)) {
            return;
        }
        Player shooter = (Player) arrow.getShooter();
        for (PotionEffect effects : arrow.getCustomEffects()) {
            if (effects.getAmplifier() > 4
                    || effects.getDuration() > 12000) {
                event.setCancelled(true);
                Utils.sendMessage(shooter, ConfigCache.IllegalPotionMessage);
                break;
            }
        }
    }
}
