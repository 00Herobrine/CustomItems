package org.x00hero.CustomItems.Custom.Block.Events;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class CustomBlockEvent extends BlockEvent {
    public static HandlerList handlerList = new HandlerList();
    public CustomBlockEvent(Block theBlock) {
        super(theBlock);
    }

    @Override
    public HandlerList getHandlers() {
        return getHandlers();
    }
}
