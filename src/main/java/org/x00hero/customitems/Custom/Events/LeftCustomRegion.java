package org.x00hero.customitems.Custom.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.x00hero.customitems.Custom.CustomRegion;

import javax.annotation.Nullable;

public class LeftCustomRegion extends CustomRegionEvent {
    @Nullable
    private final PlayerMoveEvent event;
    public LeftCustomRegion(Player player, CustomRegion region, @Nullable PlayerMoveEvent event) {
        super(player, region);
        this.event = event;
    }

    @Nullable
    public PlayerMoveEvent getEvent() { return event; }
}
