package org.x00hero.CustomItems.Custom.Item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomItem extends ItemStack {
    public String ID = CustomItem.class.getName();
    public CustomItem(ItemStack itemStack) {
        super(itemStack);
        if(itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName()) setName(itemStack.getItemMeta().getDisplayName());
        setLore(itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>());
        setCustomData();
    }
    public CustomItem(Material material) { super(material); }
    public CustomItem(Material material, int amount) { super(material, amount); }
    public CustomItem(Material material, String name) {
        super(material);
        setName(name);

    }
    public CustomItem(Material material, int amount, String name) {
        super(material, amount);
        setName(name);
    }
    public CustomItem(Material material, String name, String lore) {
        super(material);
        setName(name);
        setLore(lore);
    }
    public CustomItem(Material material, int amount, String name, String lore) {
        super(material, amount);
        setName(name);
        setLore(lore);
    }

    public void onLeftClick(PlayerInteractEvent e) {

    }

    public void onRightClick() {

    }

    public void onPlace() {

    }

    public static String formatColors(String string) { return formatColors(string, '&'); }
    public static String formatColors(String string, char translateChar) { return ChatColor.translateAlternateColorCodes(translateChar, string); }
    public String getName() { return getItemMeta().hasDisplayName() ? getItemMeta().getDisplayName() : getType().name(); }
    public void setName(String name) { ItemMeta meta = getItemMeta(); meta.setDisplayName(formatColors(name)); super.setItemMeta(meta); }
    public void setLore(String rawLore) { super.getItemMeta().setLore(Arrays.stream(formatColors(rawLore).split("\n")).toList()); }
    public void setLore(List<String> lore) { ItemMeta meta = getItemMeta() == null ? Bukkit.getItemFactory().getItemMeta(getType()) : getItemMeta(); meta.setLore(lore); super.setItemMeta(meta); }
    public Object getCustomData(NamespacedKey key, PersistentDataType type) { return getItemMeta().getPersistentDataContainer().getOrDefault(key, type, "1"); }
    public void setCustomData(NamespacedKey key, PersistentDataType type, Object input) { setCustomData(this, key, type, input); }
    public static void setCustomData(ItemStack itemStack, NamespacedKey key, PersistentDataType type, Object input) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta != null) itemMeta.getPersistentDataContainer().set(key, type, input);
        itemStack.setItemMeta(itemMeta);
    }
}
