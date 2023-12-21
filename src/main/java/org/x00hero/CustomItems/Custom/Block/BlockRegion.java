package org.x00hero.CustomItems.Custom.Block;

import org.bukkit.util.BoundingBox;
import org.x00hero.CustomItems.Custom.Region.CustomRegion;

public class BlockRegion extends CustomRegion {
    private CustomBlock block;
    public BlockRegion(CustomBlock block, BoundingBox region) {
        //super(region);
        this.block = block;
    }
}
