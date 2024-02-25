package org.surf.modules.patches;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.surf.util.SecondPassEvent;
import org.surf.util.Utils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Offhand implements Listener {
	private final Map<UUID, Integer> offhandMap = new ConcurrentHashMap<>();

	private final List<Material> MATERIALS = Lists.newArrayList(Material.BOOK,Material.WRITTEN_BOOK, Material.WRITABLE_BOOK);

	public Offhand() {
		// add all shulkers
		for (Material material : Material.values()) {
			if (material.toString().contains("SHULKER_BOX")) {
				MATERIALS.add(material);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		ItemStack mainHand = event.getMainHandItem();
		ItemStack offHand = event.getOffHandItem();
		if (isItemNeedToCheck(mainHand)) {
			check(player);
		} else if (isItemNeedToCheck(offHand)) {
			check(player);
		}
	}

	private void check(Player player) {
		if (offhandMap.containsKey(player.getUniqueId())) {
			offhandMap.replace(player.getUniqueId(), offhandMap.get(player.getUniqueId()) + 1);
		} else {
			offhandMap.put(player.getUniqueId(), 1);
		}
		if (offhandMap.get(player.getUniqueId()) > 10) {
			Utils.kickPlayer(player, Utils.getPrefix() + "&cPacket Exploit Detected");
		}
	}

	@EventHandler
	public void onSecond(SecondPassEvent event) {
		// clear map
		offhandMap.clear();
	}

	private boolean isItemNeedToCheck(ItemStack itemStack) {
		return MATERIALS.contains(itemStack.getType());
	}
}

