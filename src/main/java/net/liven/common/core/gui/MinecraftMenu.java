package net.liven.common.core.gui;


import lombok.Getter;
import net.liven.skyblock.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public abstract class MinecraftMenu {

    protected Inventory inventory;
    @Getter
    int slots;

    public void build(int slots, String name){
        this.slots = slots;
        inventory = Bukkit.createInventory(null, slots, Utils.translateColors(name));
    }

    public Inventory build(){
        return inventory;
    }


    public void populateInventory(){
        for (int i = 0; i < slots; i++) {
            inventory.setItem(i, getEmptyGlassPane());
        }
    }

    public ItemStack getEmptyGlassPane() {
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET.toString());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public abstract void execute();


    public ItemStack getItem(String name, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColors(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getItem(String name, Material material, int count) {
        ItemStack itemStack = new ItemStack(material, count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColors(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
