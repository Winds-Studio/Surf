package cn.dreeam.surf.modules.patches;

import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class IllegalDamageAndPotionCheck implements Listener {

    // Entity gets damage
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        // Player => Entity
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (event.getDamage() > 30) {
                event.setCancelled(true);
                Utils.sendMessage(damager, ConfigCache.IllegalDamageMessage);
            }
        } else {
            // Entity => Entity
            Entity entity = event.getDamager();

            if (entity instanceof LivingEntity) {
                LivingEntity damager = (LivingEntity) entity;
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

        if (effect != null) {
            if (event.getEntity().getUniqueId().toString().equals("dba7869d-1ab8-3e2e-86cd-2d425324c496"))
                System.out.println(effect.getAmplifier() + "|" + effect.getDuration() + "|" + effect.getType());
            if (effect.getAmplifier() > 5 || effect.getDuration() > 12000) {
                event.setCancelled(true);
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    Utils.sendMessage(player, ConfigCache.IllegalPotionMessage);
                }
            }
        }
    }

    // Arrow shoot by player
    @EventHandler(ignoreCancelled = true)
    public void onHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow) || !(event.getEntity().getShooter() instanceof Player)
                || !(event.getHitEntity() instanceof Player)) {
            return;
        }
        Arrow arrow = (Arrow) event.getEntity();
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

    @EventHandler(ignoreCancelled = true)
    public void onThrow(PotionSplashEvent event) {
        if (!(event.getPotion().getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getPotion().getShooter();

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

    // Check Player consume Potion with illegal potion effects
    // Dreeam TODO: Check wheter need add foods with illegal effects
    @EventHandler(ignoreCancelled = true)
    public void PlayerInteractEvent(PlayerItemConsumeEvent e) {
        if (!e.getItem().getType().equals(Material.POTION) || !e.getItem().hasItemMeta()) {
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

    // Check Potion/Arrow/Trident with illegal potion effects dispense from dispenser
    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        String material = event.getItem().getType().name();

        // Needs to add more items if they are added in newer MC version,
        // or remove material check
        if (material.contains("POTION") || material.contains("ARROW") || material.contains("TRIDENT")) {
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
