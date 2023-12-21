package org.x00hero.Stamina;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.x00hero.Stamina.Events.Custom.StaminaDrainEvent;
import org.x00hero.Stamina.Events.Custom.StaminaRegenEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static org.x00hero.customitems.Main.*;

public class StaminaController {
    private static YamlConfiguration config;
    private static HashMap<UUID, StaminaContainer> registered = new HashMap<>();
    private static File playersFolder;

    public static void LoadConfig() {
        playersFolder = new File(plugin.getDataFolder(), "Players");
        config = YamlConfiguration.loadConfiguration(getStaminaFile());
        StaminaBarUpdater();
        StaminaUpdater();
    }

    public static void RegisterPlayer(Player player) {
        ConfigurationSection section = config.getConfigurationSection("Config");
        if(config.getString("drain-config").equalsIgnoreCase("global"))
            section = config.getConfigurationSection("Config");
        else section = YamlConfiguration.loadConfiguration(getPlayerStaminaFile(player)).getDefaultSection();
        StaminaContainer stamina = new StaminaContainer(player, section);
        registered.put(player.getUniqueId(), stamina);
        Log("Registered: " + stamina);
    }

    public static File getPlayerStaminaFile(Player player) { return getPlayerStaminaFile(player.getUniqueId()); }
    public static File getPlayerStaminaFile(UUID uuid) {
        if(!playersFolder.exists()) if(!playersFolder.mkdirs()) return null;
        File staminaFile = new File(plugin.getDataFolder(), uuid.toString() + File.separator + "stamina.yml");
        try {
            ((FileConfiguration) config.getConfigurationSection("Config")).save(staminaFile);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return staminaFile;
    }
    public static File getStaminaFile() {
        File staminaFile = new File(plugin.getDataFolder(), "stamina.yml");
        if(!staminaFile.exists()) plugin.saveResource("stamina.yml", false);
        return staminaFile;
    }

    public static StaminaContainer getStaminaContainer(Player player) {
        if(!registered.containsKey(player.getUniqueId())) RegisterPlayer(player);
        return registered.get(player.getUniqueId());
    }
    public static boolean drainStamina(Player player, DrainCause cause) {
        StaminaContainer stamina = getStaminaContainer(player);
        return drainStamina(player, cause, stamina.getDrainAmount(cause), stamina.getDrainInterval());
    }
    public static boolean drainStamina(Player player, double interval, DrainCause cause) {
        StaminaContainer stamina = new StaminaContainer(getStaminaContainer(player));
        return drainStamina(player, cause, stamina.getDrainAmount(cause), interval);
    }
    private static boolean drainStamina(Player player, DrainCause cause, double amount, double interval) {
        StaminaContainer stamina = new StaminaContainer(getStaminaContainer(player)); // Storing initial
        if(stamina.hasAmount(amount)) {
            StaminaContainer newStamina = getStaminaContainer(player).drain(amount, interval); // Updating it and storing
            double updatedAmount = newStamina.getAmount();
            double delay = stamina.getRegenDelay(cause);
            if(updatedAmount <= 0) { newStamina.setRegenCooldown(stamina.getExhaustedRegenDelay()); newStamina.setExhausted(true); }
            else newStamina.setRegenCooldown(delay);
            CallEvent(new StaminaDrainEvent(player, stamina, newStamina, cause));
            UpdateStaminaBar(player);
            return true;
        }
        return false;
    }

    public static void regenStamina(Player player, DrainCause cause, double amount) {
        StaminaContainer stamina = new StaminaContainer(getStaminaContainer(player));
        StaminaContainer newStamina = getStaminaContainer(player).regen(amount);
        if(newStamina.isExhausted() && newStamina.hasAmount(config.getDouble("Exhausted.reset-amount"))) newStamina.setExhausted(false);
        CallEvent(new StaminaRegenEvent(player, stamina, newStamina, cause));
        UpdateStaminaBar(player);
    }

    public static int id = -1;
    public static void StaminaBarUpdater() {
        if(id != -1) { Bukkit.getScheduler().cancelTask(id); id = -1; } // idk resetting the -1 is kinda pointless but better to be consistent
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(StaminaContainer controller : registered.values()) {
                Player player = controller.getHolder();
                if(player == null) { unregister(controller.getHolderID()); continue; }
                StaminaCheck(player, null);
                UpdateStaminaBar(player);
            }
        }, 0, config.getInt("stamina-bar-update-rate"));
    }

    public static void UpdateStaminaBar(Player player) {
        StaminaContainer stamina = getStaminaContainer(player);
        player.setExp(stamina.getPercentage());
        player.setLevel((int) (stamina.getTimeUntilRegen() / 1000));
    }

    public static int mainID = -1;
    public static void StaminaUpdater() {
/*        if(mainID != -1) { Bukkit.getScheduler().cancelTask(mainID); mainID = -1; }
        mainID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(StaminaContainer container : registered.values()) {
                StaminaCheck(player);
            }
            StaminaCheck(player);
        }, 0, config.getInt("stamina-tick-rate"));*/
    }

    public static void StaminaCheck(Player player, PlayerMoveEvent event) {
        StaminaContainer stamina = getStaminaContainer(player);
        //Log("Current: " + System.currentTimeMillis() + "\n" + stamina.toString());
        GameMode gameMode = player.getGameMode();
        if(gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR) return;
        if(!stamina.DrainDelayed()) return; // an internal interval so it doesn't spam drain
        DrainCause state = DrainCause.IDLE;
        double mag = getMagnitude(player.getVelocity());
        //Log("Velocity " + player.getVelocity() + " L: " + mag);
        if(mag > 0) state = DrainCause.WALK;
        if(player.isSprinting()) {
            state = DrainCause.SPRINT;
            if(stamina.isExhausted()) {
                float original = player.getWalkSpeed();
                int food = player.getFoodLevel();
                player.setWalkSpeed(0f);
                player.setFoodLevel(2);
                player.setWalkSpeed(original);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.setFoodLevel(food);
                }, 10L);
            }
        } else if(player.isSneaking()) {
            state = DrainCause.SNEAKING;
        }
        double drainAmount = stamina.getDrainAmount(state);
        if(drainAmount > 0) drainStamina(player, state, drainAmount, stamina.getDrainInterval());
        else if(stamina.getTimeUntilRegen() <= 0 && stamina.RegenDelayed()) regenStamina(player, state, stamina.getRegenAmount(state));
    }

    public static double getMagnitude(Vector vector) { return vector.getX() + vector.getZ(); }

    public static void unregister(Player player) { unregister(player.getUniqueId()); }
    public static void unregister(UUID uuid) { registered.remove(uuid); }
}
