package org.x00hero.CustomItems.Custom;

import org.bukkit.inventory.ItemStack;
import org.x00hero.CustomItems.Config;

import java.security.SecureRandom;
import java.util.Base64;

import static org.x00hero.CustomItems.Config.seed;

public class Validator {
    public static boolean isCustomItem(ItemStack itemStack) {
        return false;
    }
    public static String encode(String ID) {
        SecureRandom random = new SecureRandom(seed);
        byte[] keyBytes = new byte[Config.getByteCount()];
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
    public static String decode(String base64, String ID) {
        byte[] keyBytes = Base64.getDecoder().decode(base64);
        byte[] seed = ID.getBytes();
        byte[] combinedBytes = combineAndDecode(keyBytes, seed);
        return new String(combinedBytes);
    }

    private static byte[] combineAndEncode(byte[] keyBytes, byte[] seed) {
        byte[] combinedBytes = new byte[keyBytes.length];
        for (int i = 0; i < keyBytes.length; i++) {
            combinedBytes[i] = (byte) (keyBytes[i] + seed[i % seed.length]);
        }
        return combinedBytes;
    }
    private static byte[] combineAndDecode(byte[] keyBytes, byte[] seed) {
        byte[] combinedBytes = new byte[keyBytes.length];
        for (int i = 0; i < keyBytes.length; i++) {
            combinedBytes[i] = (byte) (keyBytes[i] ^ seed[i % seed.length]);
        }
        return combinedBytes;
    }
}
