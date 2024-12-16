package cn.dreeam.surf.modules.antiillegal;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.ItemUtil;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.Util;
import org.bukkit.Material;
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
        if (!Config.checkIllegalDamageEnabled) return;

        // Player => Entity
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (!damager.getInventory().getItemInMainHand().getType().toString().equals("MACE") && event.getDamage() > 30) { // Dreeam TODO: check illegal mace
                event.setCancelled(true);
                damager.getInventory().remove(damager.getInventory().getItemInMainHand()); // Seems only can use item on main hand to attack
                MessageUtil.sendMessage(damager, Config.checkIllegalDamageMessage);
            }
        } else {
            // Entity => Entity
            Entity entity = event.getDamager();

            if (entity instanceof LivingEntity) {
                LivingEntity damager = (LivingEntity) entity;
                // Only check entities using illegal items
                if (damager.getEquipment() != null && damager.getEquipment().getItemInMainHand().hasItemMeta()) {
                    double damage = event.getDamage();

                    if (damage > 30) {
                        String itemName = ItemUtil.getItemDisplayName(damager.getEquipment().getItemInMainHand());

                        event.setCancelled(true);
                        damager.getEquipment().setItemInMainHand(null); // Seems only can use item on main hand to attack

                        MessageUtil.println(String.format(
                                "%s try to use illegal item %s with damage %s at %s",
                                damager.getName(),
                                itemName,
                                damage,
                                MessageUtil.locToString(damager.getLocation())
                        ));
                    }
                }
            }

            // Player: Projectile -> Entity
            // Dreeam TODO: this is temp fix, need to rewrite.
            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile) entity;
                if (event.getDamage() > 30) {
                    event.setCancelled(true);
                    if (projectile.getShooter() instanceof Player) {
                        Player shooter = (Player) projectile.getShooter();
                        if (shooter.getInventory().getItemInMainHand().getType().toString().endsWith("BOW")) shooter.getInventory().setItemInMainHand(null); // Seems only can use item on main hand to attack
                        MessageUtil.sendMessage(shooter, Config.checkIllegalDamageMessage);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Config.checkIllegalPotionEnabled) return;

        Player player = event.getPlayer();

        if (!player.getActivePotionEffects().isEmpty()) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (ItemUtil.isIllegalEffect(effect)) {
                    player.removePotionEffect(effect.getType());
                }
            }
        }
    }

    // Check Entity gets illegal potion effects
    @EventHandler(ignoreCancelled = true)
    public void onPotion(EntityPotionEffectEvent event) {
        if (!Config.checkIllegalPotionEnabled) return;

        PotionEffect effect = event.getNewEffect();

        if (effect != null) {
            if (ItemUtil.isIllegalEffect(effect)) {
                event.setCancelled(true);
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    MessageUtil.sendMessage(player, Config.checkIllegalPotionMessage);
                }
            }
        }
    }

    // Arrow shoot by player
    @EventHandler(ignoreCancelled = true)
    public void onHit(ProjectileHitEvent event) {
        if (!Config.checkIllegalPotionEnabled) return;

        if (!(event.getEntity() instanceof Arrow) || !(event.getEntity().getShooter() instanceof Player)
                || !(event.getHitEntity() instanceof Player)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        Player shooter = (Player) arrow.getShooter();

        for (PotionEffect effect : arrow.getCustomEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                event.setCancelled(true);
                MessageUtil.sendMessage(shooter, Config.checkIllegalPotionMessage);
                break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onThrow(PotionSplashEvent event) {
        if (!Config.checkIllegalPotionEnabled) return;

        if (!(event.getPotion().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPotion().getShooter();
        ItemStack pot = event.getPotion().getItem();

        for (PotionEffect effect : event.getPotion().getEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                event.setCancelled(true);
                player.getInventory().remove(pot);
                MessageUtil.sendMessage(player, Config.checkIllegalPotionMessage);
                break;
            }
        }
    }

    // Check Player consume Potion with illegal potion effects
    // Dreeam TODO: Check wheter need add foods with illegal effects
    @EventHandler(ignoreCancelled = true)
    public void PlayerInteractEvent(PlayerItemConsumeEvent e) {
        if (!Config.checkIllegalPotionEnabled) return;

        if (!e.getItem().getType().toString().contains("POTION") || !e.getItem().hasItemMeta()) {
            return;
        }

        PotionMeta potion = (PotionMeta) e.getItem().getItemMeta();

        for (PotionEffect effect : potion.getCustomEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                e.setCancelled(true);
                e.getPlayer().getInventory().remove(e.getItem());
                MessageUtil.sendMessage(e.getPlayer(), Config.checkIllegalPotionMessage);
                break;
            }
        }
    }

    // Check Potion/Arrow/Trident with illegal potion effects dispense from dispenser
    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (!Config.checkIllegalPotionEnabled) return;

        String material = event.getItem().getType().name();

        // Needs to add more items if they are added in newer MC version,
        // or remove material check
        if (material.contains("POTION") || material.contains("ARROW") || material.contains("TRIDENT")) {
            if (event.getItem().hasItemMeta() && event.getItem().getItemMeta() instanceof PotionMeta) {
                PotionMeta pot = (PotionMeta) event.getItem().getItemMeta();
                Dispenser disp = (Dispenser) event.getBlock().getState();

                for (PotionEffect effect : pot.getCustomEffects()) {
                    if (ItemUtil.isIllegalEffect(effect)) {
                        event.setCancelled(true);
                        disp.getInventory().remove(event.getItem());

                        // One illegal potion effect appear, remove whole item
                        // then break the for loop.
                        break;
                    }
                }

                MessageUtil.println(String.format(
                        "Detected item %s with illegal effects at %s",
                        ItemUtil.getItemDisplayName(event.getItem()),
                        MessageUtil.locToString(event.getBlock().getLocation())
                ));
            }
        }
    }
}
