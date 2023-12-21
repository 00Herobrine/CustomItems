package org.x00hero.customitems.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.x00hero.customitems.Custom.CustomRegion;
import org.x00hero.customitems.Custom.Events.EnteredCustomRegion;
import org.x00hero.customitems.Custom.Events.LeftCustomRegion;
import org.x00hero.customitems.Custom.Events.SustainedCustomRegion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static org.x00hero.customitems.Main.*;

public class RegionManager {
    private static HashMap<String, CustomRegion> customRegions = new HashMap<>();
    private static HashMap<UUID, String> inRegions = new HashMap<>();
    public static void LoadRegions() {
        File regionsFile = getRegionsFile();
        YamlConfiguration regionConfig = YamlConfiguration.loadConfiguration(regionsFile);
        for(String key : regionConfig.getKeys(false)) {
            ConfigurationSection section = regionConfig.getConfigurationSection(key);
            if(section == null) continue;
            RegisterRegion(new CustomRegion(section));
        }
    }
    public static File getRegionsFile() {
        File regionsFile = new File(plugin.getDataFolder(), "regions.yml");
        if (!regionsFile.exists()) {
            try {
                Files.copy(
                        Objects.requireNonNull(plugin.getResource("regions.yml")),
                        regionsFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return regionsFile;
    }
    public static void RegisterRegion(CustomRegion customRegion) { customRegions.put(customRegion.ID, customRegion); }
    public void RemoveRegion(String id) { customRegions.remove(id); }
    public static void RegionTick() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(CustomRegion region : customRegions.values()) {
                for(Player player : region.getPlayers()) {
                    RegionCheck(player, (PlayerMoveEvent) null);
                }
            }
        }, 0, Math.abs(plugin.getConfig().getInt("Region.update-rate")));
    }
    public static void RegionCheck(Player player, PlayerMoveEvent e) {
        Vector locVector = player.getLocation().toVector();
        if(e != null) locVector = e.getTo().toVector();
        for(CustomRegion region : getRegions(player.getWorld())) {
            //Log("Checking region " + region.ID);
            double size = Math.max(region.getWidthX(), region.getWidthZ());
            double distance = region.getCenter().distance(locVector);
            if(distance > size) { /*Log(distance + " > " + size + "continuing...");*/ continue; }
            if(inRegion(locVector, region)) {
                Log("In Region!");
                if(!region.isAllowed(player)) { if(e != null) e.setCancelled(true); Log("Not Whitelisted!");
                    player.teleport(getNearestValidLocation(player, region));
                }
                if(!region.inRegion(player)) {
                    EnteredRegion(player, region);
                } else RemainedInRegion(player, region);
            } else if(region.inRegion(player)) LeftRegion(player, region);
        }
    }
    public static void RegionCheck(Player player, Vector location) {
        Vector locVector = player.getLocation().toVector();
        for(CustomRegion region : getRegions(player.getWorld())) {
            Log("Checking region " + region.ID);
            double size = Math.max(region.getWidthX(), region.getWidthZ());
            double distance = region.getCenter().distance(locVector);
            if(distance > size) { Log(distance + " > " + size + "continuing..."); continue; }
            if(inRegion(locVector, region)) {
                Log("In Region!");
                if(!region.isAllowed(player)) player.teleport(getNearestValidLocation(player, region));
                if(!region.inRegion(player)) {
                    EnteredRegion(player, region);
                } else RemainedInRegion(player, region);
            } else if(region.inRegion(player)) LeftRegion(player, region);
        }
    }
    public static boolean inRegion(Player player, CustomRegion region) { return inRegion(player.getLocation().getDirection(), region); }
    public static boolean inRegion(Vector vector, CustomRegion region) { return region.contains(vector); }
    public static void EnteredRegion(Player player, CustomRegion region) { EnteredRegion(player.getUniqueId(), region); CallEvent(new EnteredCustomRegion(player, region, null)); }
    public static void EnteredRegion(UUID uuid, CustomRegion region) { region.add(uuid); inRegions.put(uuid, region.ID); }
    public static void LeftRegion(Player player, CustomRegion region) { LeftRegion(player.getUniqueId(), region); CallEvent(new LeftCustomRegion(player, region, null)); }
    public static void LeftRegion(UUID uuid, CustomRegion region) { region.remove(uuid); inRegions.remove(uuid); }
    public static void RemainedInRegion(Player player, CustomRegion region) { RemainedInRegion(player.getUniqueId(), region); CallEvent(new SustainedCustomRegion(player, region, null)); }
    public static void RemainedInRegion(UUID uuid, CustomRegion region) { }
    public static CustomRegion[] getRegions() { return customRegions.values().toArray(new CustomRegion[0]); }
    public static CustomRegion[] getRegions(World world) {
        return customRegions.values()
                .stream()
                .filter(region -> region.getWorld() == null || region.getWorld() == world)
                .toArray(CustomRegion[]::new);
    }
    public static Location getNearestValidLocation(Player player, BoundingBox boundingBox) { return getNearestLocationOutsideBoundingBox(player.getWorld(), player.getLocation().toVector(), boundingBox); }
    private static Vector findClosestEdge(Vector current, Vector min, Vector max) { return findClosestEdge(current, new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ())); }
    private static Vector findClosestEdge(Vector current, BoundingBox boundingBox) {
        Vector center = boundingBox.getCenter();
        Vector direction = center.clone().subtract(current).normalize();
        Log("Direction: " + direction + " (C:" + center + ")");
        double xDistance = boundingBox.getWidthX() - current.getX();
        double yDistance = boundingBox.getHeight() - current.getY();
        double zDistance = boundingBox.getWidthZ() - current.getZ();
        double minDistance = Math.min(Math.min(xDistance, yDistance), zDistance);
        Log("minDistance: " + minDistance);
        Vector location = center.clone().add(direction.multiply(minDistance + 1));
        Log("Location: " + location);
        return location;
    }
    public static Location getNearestLocationOutsideBoundingBox(World world, Vector currentVector, BoundingBox boundingBox) {
        Vector edge = findClosestEdge(currentVector, boundingBox);
        return new Location(world, edge.getX(), edge.getY(), edge.getZ());
    }
}
