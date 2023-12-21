package org.x00hero.CustomItems.Custom.Region;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.x00hero.CustomItems.Managers.RegionManager;

import javax.annotation.Nullable;
import java.util.*;

public class CustomRegion extends BoundingBox {
    public final String ID;
    private boolean whitelisted;
    HashMap<UUID, Long> inRegion = new HashMap<>();
    List<UUID> whitelist = new ArrayList<>();
    List<UUID> blacklist = new ArrayList<>();
    @Nullable
    private World world = null;
    public CustomRegion() { this.ID = "0,0,0"; }
    public CustomRegion(String ID, int x1, int y1, int z1, int x2, int y2, int z2) { super(x1, y1, z1, x2, y2, z2); this.ID = ID; }
    public CustomRegion(ConfigurationSection section) {
        ID = section.getName();
        String[] bottomLeft = section.getString("bottomLeft").trim().split(",");
        String[] topRight = section.getString("topRight").split(",");
        whitelisted = section.getBoolean("whitelisted", false);
        int[] min = new int[3];
        int[] max = new int[3];
        for(int i = 0; i < 3; i++) {
            min[i] = Math.min(Integer.parseInt(bottomLeft[i].trim()), Integer.parseInt(topRight[i].trim()));
            max[i] = Math.max(Integer.parseInt(bottomLeft[i].trim()), Integer.parseInt(topRight[i].trim()));
        }
        resize(min[0], min[1], min[2], max[0], max[1], max[2]);
    }
    public long getEntered(Player player) { return getEntered(player.getUniqueId()); }
    public long getEntered(UUID uuid) { return inRegion.get(uuid); }
    public void add(Player player) { add(player.getUniqueId()); }
    public void add(UUID uuid) { inRegion.put(uuid, System.currentTimeMillis()); }
    public void addAllowed(Player player) { addAllowed(player.getUniqueId()); }
    public void addAllowed(UUID uuid) { whitelist.add(uuid); }
    public void addBlacklist(Player player) { addBlacklist(player.getUniqueId()); }
    public void addBlacklist(UUID uuid) { blacklist.add(uuid); }
    public void remove(Player player) { remove(player.getUniqueId()); }
    public void remove(UUID uuid) { inRegion.remove(uuid); }
    public void removeAllowed(Player player) { removeAllowed(player.getUniqueId()); }
    public void removeAllowed(UUID uuid) { whitelist.remove(uuid); }
    public void removeBlacklist(Player player) { removeBlacklist(player.getUniqueId()); }
    public void removeBlacklist(UUID uuid) { blacklist.add(uuid); }
    public void setWhitelisted(boolean state) { whitelisted = state; }
    public void setWhitelisted(Player player, boolean state) { setWhitelisted(player.getUniqueId(), state); }
    public void setWhitelisted(UUID uuid, boolean state) { if(state) whitelist.add(uuid); else whitelist.remove(uuid); }
    public boolean isAllowed(Player player) { return isAllowed(player.getUniqueId()); }
    public boolean isAllowed(UUID uuid) { return !blacklist.contains(uuid) && (!whitelisted || whitelist.contains(uuid)); }
    public boolean isWhitelisted() { return whitelisted; }
    public boolean isWhitelisted(Player player) { return isWhitelisted(player.getUniqueId()); }
    public boolean isWhitelisted(UUID uuid) { return whitelist.contains(uuid); }
    public boolean inRegion(Player player) { return inRegion(player.getUniqueId()); }
    public boolean inRegion(UUID uuid) { return inRegion.containsKey(uuid); }
    public boolean PlayerInRegion(Player player) { return RegionManager.inRegion(player, this); }
    public Player[] getPlayers() { return inRegion.keySet().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).toArray(Player[]::new); }
    @Nullable
    public World getWorld() { return world; }
    public void setWorld(@Nullable World world) { this.world = world; }
}
