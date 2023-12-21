package org.x00hero.CustomItems.Custom.Block.Events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CustomBlockPlaceEvent extends BlockPlaceEvent {
    public CustomBlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block placedAgainst, ItemStack itemInHand, Player thePlayer, boolean canBuild, EquipmentSlot hand) {
        super(placedBlock, replacedBlockState, placedAgainst, itemInHand, thePlayer, canBuild, hand);
    }
}
