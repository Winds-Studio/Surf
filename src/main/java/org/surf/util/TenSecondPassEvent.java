package org.surf.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.surf.Main;

import java.util.logging.Logger;

public class TenSecondPassEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
