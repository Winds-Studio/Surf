package org.surf.util;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Cooldown {

	private final HashMap<Player, Double> cooldowns = new HashMap<Player, Double>();

	public void setCooldown(Player player, int seconds) {
		double delay = System.currentTimeMillis() + (seconds * 1000);
		cooldowns.put(player, delay);
	}

	public int getCooldown(Player player) {
		return Math.toIntExact(Math.round((cooldowns.get(player) - System.currentTimeMillis()) / 1000));
	}

	public boolean checkCooldown(Player player) {
		return !cooldowns.containsKey(player) || cooldowns.get(player) <= System.currentTimeMillis();
	}

}