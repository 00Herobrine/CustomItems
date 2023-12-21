package org.x00hero.CustomItems.Custom.Item.Events;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityCombustEvent;

public class CustomItemCombustEvent extends EntityCombustEvent {
    public CustomItemCombustEvent(Entity combustee, int duration) {
        super(combustee, duration);
    }
}
