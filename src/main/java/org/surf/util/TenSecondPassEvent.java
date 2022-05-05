package org.surf.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.surf.Main;

import java.util.logging.Logger;

public class TenSecondPassEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Logger logger;
	private final Main plugin;

	public TenSecondPassEvent(Logger logger, Main main) {
		this.logger = logger;
		plugin = main;
	}

	public Main getPlugin() {
		return plugin;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
