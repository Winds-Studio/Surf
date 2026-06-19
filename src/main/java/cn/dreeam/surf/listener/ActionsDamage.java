package cn.dreeam.surf.listener;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.item.ItemUtil;
import cn.dreeam.surf.util.MessageUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ActionsDamage implements Listener {

    // Entity gets damage
    @EventHandler(ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent event) {
        if (!Config.checkIllegalDamageEnabled) return;

        // Player => Entity
        if (event.getDamager() instanceof Player damager) {
            if (!damager.getInventory().getItemInMainHand().getType().toString().equals("MACE") && event.getDamage() > 30) { // Dreeam TODO: check illegal mace
                event.setCancelled(true);
                damager.getInventory().remove(damager.getInventory().getItemInMainHand()); // Seems only can use item on main hand to attack
                MessageUtil.sendMessage(damager, Config.checkIllegalDamageMessage);
            }
        } else {
            // Entity => Entity
            Entity entity = event.getDamager();

            if (entity instanceof LivingEntity damager) {
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
            if (entity instanceof Projectile projectile) {
                if (event.getDamage() > 30) {
                    event.setCancelled(true);
                    if (projectile.getShooter() instanceof Player shooter) {
                        if (shooter.getInventory().getItemInMainHand().getType().toString().endsWith("BOW")) shooter.getInventory().setItemInMainHand(null); // Seems only can use item on main hand to attack
                        MessageUtil.sendMessage(shooter, Config.checkIllegalDamageMessage);
                    }
                }
            }
        }
    }
}
