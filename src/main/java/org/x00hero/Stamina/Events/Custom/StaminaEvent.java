package org.x00hero.Stamina.Events.Custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.x00hero.Stamina.StaminaContainer;

public class StaminaEvent extends Event {
    public static HandlerList handlerList = new HandlerList();
    private final Player player;
    private final StaminaContainer oldStamina;
    private final StaminaContainer newStamina;

    public StaminaEvent(Player player, StaminaContainer oldStamina, StaminaContainer newStamina) {
        this.player = player;
        this.oldStamina = oldStamina;
        this.newStamina = newStamina;
    }

    public Player getPlayer() { return player; }
    public double getOldAmount() { return oldStamina.getAmount(); }
    public double getNewAmount() { return newStamina.getAmount(); }
    public double getDifference() { return Math.abs(getOldAmount() - getNewAmount()); }
    public StaminaContainer getOldStamina() { return oldStamina; }
    public StaminaContainer getNewStamina() { return newStamina; }

    @Override
    public HandlerList getHandlers() { return handlerList; }
    public static HandlerList getHandlerList() { return handlerList; }
}
