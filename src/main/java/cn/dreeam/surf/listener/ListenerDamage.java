package cn.dreeam.surf.listener;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerDamage implements Listener {

    // Entity gets damage
    @EventHandler(ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent event) {
        if (!Config.GeneralChecks.checkIllegalDamage) return;

        final int maxGeneralDamage = Config.GeneralChecks.checkIllegalDamageMaxGeneral;

        // Player => Entity
        if (event.getDamager() instanceof Player damager) {
            final int maxAllowedDamage = getMaxAllowedDamage(damager.getInventory().getItemInMainHand());

            if (event.getDamage() > maxAllowedDamage) {
                event.setCancelled(true);

                // Seems only can use item on main hand to attack
                damager.getInventory().remove(damager.getInventory().getItemInMainHand());

                MessageUtil.sendMessage(damager, Config.GeneralChecks.checkIllegalDamageMessage);
            }
        } else {
            // Entity => Entity
            final Entity entity = event.getDamager();

            if (entity instanceof LivingEntity damager) {
                // Only check entities using illegal items
                if (damager.getEquipment() != null) {
                    final ItemStack mainHandWeapon = damager.getEquipment().getItemInMainHand();

                    if (mainHandWeapon.hasItemMeta()) {
                        final double damage = event.getDamage();
                        final int maxAllowedDamage = getMaxAllowedDamage(mainHandWeapon);

                        if (damage > maxAllowedDamage) {
                            String itemName = ItemUtil.getItemDisplayName(mainHandWeapon);

                            event.setCancelled(true);

                            // Seems only can use item on main hand to attack
                            damager.getEquipment().setItemInMainHand(null);

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
            }

            // Player: Projectile -> Entity
            // Dreeam TODO: this is temp fix, need to rewrite.
            if (entity instanceof Projectile projectile) {
                if (event.getDamage() > maxGeneralDamage) {
                    event.setCancelled(true);
                    if (projectile.getShooter() instanceof Player shooter) {
                        // Seems only can use item on main hand to attack
                        if (ItemUtil.isBowWeapon(shooter.getInventory().getItemInMainHand()))
                            shooter.getInventory().setItemInMainHand(null);
                        MessageUtil.sendMessage(shooter, Config.GeneralChecks.checkIllegalDamageMessage);
                    }
                }
            }
        }
    }

    private static int getMaxAllowedDamage(ItemStack i) {
        return i.getType() == XMaterial.MACE.get() ? Config.GeneralChecks.checkIllegalDamageMaxMace : Config.GeneralChecks.checkIllegalDamageMaxGeneral;
    }
}
