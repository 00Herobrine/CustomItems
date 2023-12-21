package org.x00hero.CustomItems.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static org.x00hero.CustomItems.Managers.RegionManager.RegionCheck;

public class PlayerMovement implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        RegionCheck(player, e);
    }
}
