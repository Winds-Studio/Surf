package cn.dreeam.surf.util;

import org.jetbrains.annotations.NotNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SecondPassEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
