package org.surf.listeners.antiillegal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.surf.Main;

public class SwapOffhand implements Listener {
    Main plugin;

    public SwapOffhand(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        try {
            if (plugin.getConfig().getBoolean("Antiillegal.PlayerSwapOffhand-Enabled")) {
                Player player = event.getPlayer();
                plugin.getItemUtils().deleteIllegals(player.getInventory());
            }
            for (ItemStack itemStack : event.getPlayer().getInventory().getContents()) {
                if (plugin.getConfigBoolean("Antiillegal.Delete-Stacked-Totem")) {
                    if (itemStack != null) {
                        if (itemStack.getType() == Material.TOTEM_OF_UNDYING) {
                            if (itemStack.getAmount() > itemStack.getMaxStackSize()) {
                                itemStack.setAmount(itemStack.getMaxStackSize());
                            }
                        }
                    }
                }
            }
        } catch (Error | Exception throwable) {

        }
    }
}