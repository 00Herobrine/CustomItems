package org.x00hero.CustomItems.Managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.x00hero.CustomItems.Config;
import org.x00hero.CustomItems.Custom.Item.CustomItem;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import static org.x00hero.Main.plugin;

public class CustomItemManager {
    private static HashMap<UUID, CustomItem> customItems = new HashMap<>();
    private static HashMap<String, CustomItem> registeredItems = new HashMap<>();
    public static void registerItems() {
        for(File file : getCustomItems()) {
            if(!file.getName().endsWith(".yml")) return;
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        }
    }
    public static void giveCustomItem(Player player, String ID) {

    }
    public static boolean isCustomItem(ItemStack itemStack) {
        CustomItem item = new CustomItem(itemStack);
        String data = (String) item.getCustomData(Config.getCustomItemKey(), PersistentDataType.STRING);

    }
    public static CustomItem getCustomItem(String UUID, int amount) {
        //CustomItem item = new CustomItem()
        return null;
    }
    private static void registerCustomItem(CustomItem customItem) {

    }

    public static File[] getCustomItems() { return getCustomItemsFolder().listFiles(); }
    public static File getCustomItemsFolder() {
        File customItemsFolder = new File(plugin.getDataFolder(), "Items");
        if(!customItemsFolder.exists()) customItemsFolder.mkdirs();
        return customItemsFolder;
    }
}
