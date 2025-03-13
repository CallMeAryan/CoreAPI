package net.liven.coreapi.gui;


import lombok.Getter;
import net.liven.coreapi.utils.ChatUtils;
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
        inventory = Bukkit.createInventory(null, slots, ChatUtils.translateColors(name));
    }

    public Inventory build(){
        return inventory;
    }


    public void populateInventory(Material material, short data) {
        for (int i = 0; i < slots; i++) {
            inventory.setItem(i, getFillerItem(material, data));
        }
    }

    public ItemStack getFillerItem(Material material, short data) {
        ItemStack fillerItem = new ItemStack(material, 1, data);
        ItemMeta fillerItemMeta = fillerItem.getItemMeta();
        fillerItemMeta.setDisplayName(ChatColor.RESET.toString());
        fillerItem.setItemMeta(fillerItemMeta);
        return fillerItem;
    }



    public abstract void execute();


    public ItemStack getItem(String name, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtils.translateColors(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getItem(String name, Material material, int count) {
        ItemStack itemStack = new ItemStack(material, count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtils.translateColors(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
