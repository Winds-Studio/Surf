package org.surf.listeners.patches;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.surf.Main;
import org.surf.util.SecondPassEvent;
import org.surf.util.Utils;

import java.util.HashMap;

public class PacketElytraFly implements Listener {
	HashMap<Player, Integer> elytraHashMap = new HashMap<>();
	Main plugin;

	public PacketElytraFly(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onToggleGlide(EntityToggleGlideEvent event) {
		try {
			if (plugin.getConfigBoolean("Elytra.PacketElytraFly-Enabled")) {
				if (event.getEntity() instanceof Player) {
					Player player = (Player) event.getEntity();
					if (elytraHashMap.containsKey(player)) {
						elytraHashMap.replace(player, elytraHashMap.get(player) + 1);
					} else {
						elytraHashMap.put(player, 1);
					}
					if (elytraHashMap.get(player) > 6) {
						elytraHashMap.remove(player);
						ItemStack chest = player.getInventory().getChestplate();
						if (chest != null && chest.getType() == Material.ELYTRA) {
							player.setGliding(false);
							if (plugin.getConfigBoolean("Elytra.SendMessage")) {
								Utils.sendMessage(player, plugin.getConfig().getString("Elytra.Message"));
							}
						}
					}
				}
			}
		} catch (Error | Exception throwable) {

		}
	}

	@EventHandler
	public void onSecondPass(SecondPassEvent event) {
		Utils.secondPass(elytraHashMap);
	}
}
