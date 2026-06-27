package cn.dreeam.surf.listener;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.PlatformUtil;
import cn.dreeam.surf.util.item.ItemUtil;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.block.Container;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class ActionsPotion implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (!Config.GeneralChecks.checkIllegalPotion) return;

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
    private void onPotion(EntityPotionEffectEvent event) {
        if (!Config.GeneralChecks.checkIllegalPotion) return;

        PotionEffect effect = event.getNewEffect();

        if (effect != null) {
            if (ItemUtil.isIllegalEffect(effect)) {
                event.setCancelled(true);
                if (event.getEntity() instanceof Player player) {
                    MessageUtil.sendMessage(player, Config.GeneralChecks.checkIllegalPotionMessage);
                }
            }
        }
    }

    // Arrow shoot by player
    @EventHandler(ignoreCancelled = true)
    private void onHit(ProjectileHitEvent event) {
        if (!Config.GeneralChecks.checkIllegalPotion) return;

        if (!(event.getEntity() instanceof Arrow arrow) || !(arrow.getShooter() instanceof Player shooter)
                || !(event.getHitEntity() instanceof Player)) {
            return;
        }

        final boolean cancellable = PlatformUtil.isNewerAndEqual(16, 5);

        for (PotionEffect effect : arrow.getCustomEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                if (cancellable) {
                    event.setCancelled(true);
                } else {
                    shooter.getInventory().remove(XMaterial.TIPPED_ARROW.get());
                }
                MessageUtil.sendMessage(shooter, Config.GeneralChecks.checkIllegalPotionMessage);
                break;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onThrow(PotionSplashEvent event) {
        if (!Config.GeneralChecks.checkIllegalPotion) return;

        if (!(event.getPotion().getShooter() instanceof Player player)) {
            return;
        }

        ItemStack pot = event.getPotion().getItem();

        for (PotionEffect effect : event.getPotion().getEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                event.setCancelled(true);
                player.getInventory().remove(pot);
                MessageUtil.sendMessage(player, Config.GeneralChecks.checkIllegalPotionMessage);
                break;
            }
        }
    }

    // Check Player consume Potion with illegal potion effects
    // Dreeam TODO: Check wheter need add foods with illegal effects
    @EventHandler(ignoreCancelled = true)
    private void onPotionConsume(PlayerItemConsumeEvent e) {
        if (!Config.GeneralChecks.checkIllegalPotion) return;

        final ItemStack item = e.getItem();

        if (!ItemUtil.isPotion(item) || !item.hasItemMeta()) {
            return;
        }

        PotionMeta potion = (PotionMeta) item.getItemMeta();

        for (PotionEffect effect : potion.getCustomEffects()) {
            if (ItemUtil.isIllegalEffect(effect)) {
                e.setCancelled(true);
                e.getPlayer().getInventory().remove(item);
                MessageUtil.sendMessage(e.getPlayer(), Config.GeneralChecks.checkIllegalPotionMessage);
                break;
            }
        }
    }

    // Check Potion/Arrow/Trident with illegal potion effects dispense from dispenser
    @EventHandler(ignoreCancelled = true)
    private void onDispense(BlockDispenseEvent event) {
        if (!Config.GeneralChecks.checkIllegalPotion) return;

        String material = event.getItem().getType().name();

        // TODO (Check this): Needs to add more items if they are added in newer MC version, or remove material check
        if (material.contains("POTION") || material.contains("ARROW") || material.contains("TRIDENT")) {
            if (event.getItem().hasItemMeta() && event.getItem().getItemMeta() instanceof PotionMeta pot) {
                Container container = (Container) event.getBlock().getState();

                for (PotionEffect effect : pot.getCustomEffects()) {
                    if (ItemUtil.isIllegalEffect(effect)) {
                        event.setCancelled(true);
                        container.getInventory().remove(event.getItem());

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
