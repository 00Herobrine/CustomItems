package org.x00hero.customitems;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.x00hero.Stamina.StaminaController;
import org.x00hero.Stamina.StaminaTest;
import org.x00hero.customitems.Events.Interactions;
import org.x00hero.customitems.Events.PlayerMovement;
import org.x00hero.customitems.Managers.RegionManager;
import org.x00hero.customitems.Tests.EventListener;

import static org.x00hero.customitems.Managers.RegionManager.RegionTick;
import static org.x00hero.customitems.Managers.RegionManager.getRegions;

public final class Main extends JavaPlugin {
    public static Main plugin;
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        RegionManager.LoadRegions();
        RegisterEvents();
        StaminaController.LoadConfig();
        int regionCount = getRegions().length;
        Bukkit.getLogger().info("Loaded " + regionCount + (regionCount == 1 ? " region." : " regions." ));
    }
    private void RegisterEvents() {
        Bukkit.getPluginManager().registerEvents(new Interactions(), plugin);
        Bukkit.getPluginManager().registerEvents(new EventListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new org.x00hero.Stamina.Events.PlayerMovement(), plugin);
        Bukkit.getPluginManager().registerEvents(new StaminaTest(), plugin);
        int updateRate = getConfig().getInt("Region.update-rate");
        if(updateRate < 0) { Log("Enabled PlayerMovement Check"); Bukkit.getPluginManager().registerEvents(new PlayerMovement(), plugin); }
        if(updateRate != -1) RegionTick();
    }

    public static void CallEvent(Event event) { Bukkit.getPluginManager().callEvent(event); }
    public static void Log(String... message) { for(String msg : message) Bukkit.getLogger().info(msg); }
}
