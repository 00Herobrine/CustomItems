package org.x00hero.Stamina;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.x00hero.Stamina.Events.Custom.PlayerFallEvent;
import org.x00hero.Stamina.Events.Custom.PlayerJumpEvent;
import org.x00hero.Stamina.Events.Custom.StaminaDrainEvent;
import org.x00hero.Stamina.Events.Custom.StaminaRegenEvent;

public class StaminaTest implements Listener {
    @EventHandler
    public void onDrain(StaminaDrainEvent e) {
        //e.getPlayer().sendMessage("Initial: " + e.getOldStamina());
        //e.getPlayer().sendMessage("New: " + e.getNewStamina());
        e.getPlayer().sendMessage("Drained " + e.getDifference() + " by " + e.getCause());
    }

    @EventHandler
    public void onRegen(StaminaRegenEvent e) {
        //e.getPlayer().sendMessage("Initial: " + e.getOldStamina());
        //e.getPlayer().sendMessage("New: " + e.getNewStamina());
        e.getPlayer().sendMessage("Regenerated " + e.getDifference() + " by " + e.getCause());
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        e.getPlayer().sendMessage("Jumped!");
    }
    @EventHandler
    public void onFall(PlayerFallEvent e) {
        e.getPlayer().sendMessage("Fell " + e.getDistance() + " blocks.");
    }
}
