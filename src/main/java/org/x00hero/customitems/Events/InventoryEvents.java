package org.x00hero.customitems.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

import static org.x00hero.customitems.Validation.Validator.isCustomItem;

public class InventoryEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(!isCustomItem(e.getCurrentItem())) return;
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if(!isCustomItem(e.getCursor())) return;
    }
    @EventHandler
    public void onItemMove(InventoryMoveItemEvent e) {
        if(!isCustomItem(e.getItem())) return;
    }
    @EventHandler
    public void onItem(InventoryPickupItemEvent e) {
        if(!isCustomItem(e.getItem().getItemStack())) return;
    }
}
