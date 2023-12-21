package org.x00hero.CustomItems.Custom.Item.Events;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.x00hero.CustomItems.Custom.Item.CustomItem;

public class CustomItemPickUpEvent extends EntityPickupItemEvent {
    public CustomItemPickUpEvent(LivingEntity entity, CustomItem item, final int remaining) {
        super(entity, (Item) item, remaining);
    }
}
