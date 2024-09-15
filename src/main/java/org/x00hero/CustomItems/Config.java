package org.x00hero.CustomItems;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Base64;

import static org.x00hero.Main.plugin;

public class Config {
    private static String secret;
    private static String drainConfig;
    private static int byteCount;
    private static int staminaTickRate;
    private static int staminaBarTickRate;
    private static int regionTickRate;
    private static NamespacedKey customItemKey;
    public static byte[] seed;


    public static void Initalize() {
        FileConfiguration config = plugin.getConfig();
        secret = config.getString("secret-key");
        drainConfig = config.getString("drain-config");
        byteCount = config.getInt("bytes");
        staminaTickRate = config.getInt("stamina-tick-rate");
        staminaBarTickRate = config.getInt("stamina-bar-tick-rate");
        regionTickRate = config.getInt("region-tick-rate");
        customItemKey = new NamespacedKey(plugin, config.getString("customItemKey"));
        seed = Base64.getDecoder().decode(Config.getSecret());
    }

    public static String getSecret() { return secret; }
    public static String getDrainConfig() { return drainConfig; }
    public static int getByteCount() { return byteCount; }
    public static int getStaminaBarTickRate() { return staminaBarTickRate; }
    public static int getStaminaTickRate() { return staminaTickRate; }
    public static int getRegionTickRate() { return regionTickRate; }
    public static NamespacedKey getCustomItemKey() { return customItemKey; }
}
