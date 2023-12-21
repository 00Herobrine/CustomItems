package org.x00hero.CustomItems.Custom.Item.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;

public class CustomItemDropEvent extends EntityDropItemEvent {
    public CustomItemDropEvent(Entity entity, Item drop) {
        super(entity, drop);
    }
}
