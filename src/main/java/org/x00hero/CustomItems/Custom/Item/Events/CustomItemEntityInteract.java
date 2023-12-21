package org.x00hero.CustomItems.Custom.Item.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CustomItemEntityInteract extends PlayerInteractEntityEvent {
    public CustomItemEntityInteract(Player who, Entity clickedEntity, EquipmentSlot hand) {
        super(who, clickedEntity, hand);
    }
}
