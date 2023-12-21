package org.x00hero.Stamina.Events.Custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFallEvent extends PlayerMoveEvent {
    public PlayerFallEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    @Override
    public void setCancelled(boolean cancel) {
        player.teleport(getFrom());
        super.setCancelled(cancel);
    }

    public double getDistance() {
        return getFrom().distance(getTo());
    }
}
