package org.x00hero.Stamina.Events.Custom;

import org.bukkit.entity.Player;
import org.x00hero.Stamina.DrainCause;
import org.x00hero.Stamina.StaminaContainer;

public class StaminaDrainEvent extends StaminaEvent {
    private final DrainCause cause;
    public StaminaDrainEvent(Player player, StaminaContainer oldStamina, StaminaContainer newStamina, DrainCause cause) {
        super(player, oldStamina, newStamina);
        this.cause = cause;
    }

    public DrainCause getCause() {
        return cause;
    }
}
