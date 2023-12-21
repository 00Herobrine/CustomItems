package org.x00hero.Stamina.Events.Custom;

import org.bukkit.entity.Player;
import org.x00hero.Stamina.DrainCause;
import org.x00hero.Stamina.StaminaContainer;

public class StaminaExhaustEvent extends StaminaDrainEvent {
    public StaminaExhaustEvent(Player player, StaminaContainer oldStamina, StaminaContainer newStamina, DrainCause cause) {
        super(player, oldStamina, newStamina, cause);
    }
}
