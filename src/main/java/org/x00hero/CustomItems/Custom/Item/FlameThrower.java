package org.x00hero.CustomItems.Custom.Item;

import org.bukkit.Material;

public class FlameThrower extends CustomItem {
    int ammo = 0;
    int maxAmmo = 5;
    public FlameThrower() {
        super(Material.STICK, "Flame Thrower", "Shoots a thing\n{ammo} flames");
    }

}
