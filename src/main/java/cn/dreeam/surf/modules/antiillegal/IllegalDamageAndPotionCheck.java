package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Util;
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
import org.bukkit.potion.PotionEffectType;

public class IllegalDamageAndPotionCheck implements Listener {

    // Entity gets damage
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!Surf.config.checkIllegalDamageEnabled()) return;

        // Player => Entity
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (event.getDamage() > 30) {
                event.setCancelled(true);
                Util.sendMessage(damager, Surf.config.checkIllegalDamageMessage());
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
                        Util.sendMessage(damager, Surf.config.checkIllegalDamageMessage());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Surf.config.checkIllegalPotionEnabled()) return;

        Player player = event.getPlayer();

        if (!player.getActivePotionEffects().isEmpty()) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (isIllegalEffect(effect)) {
                    player.removePotionEffect(effect.getType());
                }
            }
        }
    }

    // Check Entity gets illegal potion effects
    @EventHandler(ignoreCancelled = true)
    public void onPotion(EntityPotionEffectEvent event) {
        if (!Surf.config.checkIllegalPotionEnabled()) return;

        PotionEffect effect = event.getNewEffect();

        if (effect != null) {
            if (isIllegalEffect(effect)) {
                event.setCancelled(true);
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    Util.sendMessage(player, Surf.config.checkIllegalPotionMessage());
                }
            }
        }
    }

    // Arrow shoot by player
    @EventHandler(ignoreCancelled = true)
    public void onHit(ProjectileHitEvent event) {
        if (!Surf.config.checkIllegalPotionEnabled()) return;

        if (!(event.getEntity() instanceof Arrow) || !(event.getEntity().getShooter() instanceof Player)
                || !(event.getHitEntity() instanceof Player)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        Player shooter = (Player) arrow.getShooter();

        for (PotionEffect effect : arrow.getCustomEffects()) {
            if (isIllegalEffect(effect)) {
                event.setCancelled(true);
                Util.sendMessage(shooter, Surf.config.checkIllegalPotionMessage());
                break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onThrow(PotionSplashEvent event) {
        if (!Surf.config.checkIllegalPotionEnabled()) return;

        if (!(event.getPotion().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPotion().getShooter();
        ItemStack pot = event.getPotion().getItem();

        for (PotionEffect effect : event.getPotion().getEffects()) {
            if (isIllegalEffect(effect)) {
                event.setCancelled(true);
                player.getInventory().remove(pot);
                Util.sendMessage(player, Surf.config.checkIllegalPotionMessage());
                break;
            }
        }
    }

    // Check Player consume Potion with illegal potion effects
    // Dreeam TODO: Check wheter need add foods with illegal effects
    @EventHandler(ignoreCancelled = true)
    public void PlayerInteractEvent(PlayerItemConsumeEvent e) {
        if (!Surf.config.checkIllegalPotionEnabled()) return;

        if (!e.getItem().getType().equals(Material.POTION) || !e.getItem().hasItemMeta()) {
            return;
        }

        PotionMeta potion = (PotionMeta) e.getItem().getItemMeta();

        for (PotionEffect effect : potion.getCustomEffects()) {
            if (isIllegalEffect(effect)) {
                e.setCancelled(true);
                e.getPlayer().getInventory().remove(e.getItem());
                Util.sendMessage(e.getPlayer(), Surf.config.checkIllegalPotionMessage());
                break;
            }
        }
    }

    // Check Potion/Arrow/Trident with illegal potion effects dispense from dispenser
    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (!Surf.config.checkIllegalPotionEnabled()) return;

        String material = event.getItem().getType().name();

        // Needs to add more items if they are added in newer MC version,
        // or remove material check
        if (material.contains("POTION") || material.contains("ARROW") || material.contains("TRIDENT")) {
            Dispenser disp = (Dispenser) event.getBlock().getState();
            PotionMeta pot = (PotionMeta) event.getItem().getItemMeta();

            for (PotionEffect effect : pot.getCustomEffects()) {
                if (isIllegalEffect(effect)) {
                    event.setCancelled(true);
                    disp.getInventory().remove(event.getItem());
                    Util.println(event.getBlock(), Surf.config.checkIllegalPotionMessage());
                    // One illegal potion effect appear, remove whole item
                    // then break the for loop.
                    break;
                }
            }
        }
    }

    private boolean isIllegalEffect(PotionEffect effect) {
        int duration = effect.getType() == PotionEffectType.BAD_OMEN ? 120000 : 12000;

        return effect.getAmplifier() > 5 || effect.getDuration() > duration;
    }
}
