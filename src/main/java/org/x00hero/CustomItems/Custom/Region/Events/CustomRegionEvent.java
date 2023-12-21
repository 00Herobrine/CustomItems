package org.x00hero.CustomItems.Custom.Region.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.x00hero.CustomItems.Custom.Region.CustomRegion;

public class CustomRegionEvent extends Event implements Cancellable {
    public static HandlerList handlerList = new HandlerList();
    private final Player player;
    private final CustomRegion region;
    private boolean cancelled;
    public CustomRegionEvent(Player player, CustomRegion region) {
        this.player = player;
        this.region = region;
    }

    public Player getPlayer() { return player; }
    public CustomRegion getRegion() { return region; }
    public long getDuration() { return (System.currentTimeMillis() - region.getEntered(player)); }

    @Override
    public HandlerList getHandlers() { return handlerList; }
    public static HandlerList getHandlerList() { return handlerList; }

    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancel) { cancelled = cancel; }
}
