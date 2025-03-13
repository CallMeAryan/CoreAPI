package net.liven.coreapi.gui.custom;

import lombok.Getter;
import net.liven.coreapi.gui.custom.action.CommandAction;
import net.liven.coreapi.gui.custom.action.EmptyAction;
import net.liven.coreapi.gui.custom.action.ExecutableAction;
import net.liven.coreapi.gui.custom.action.ExecutableActionType;
import net.liven.coreapi.utils.ChatUtils;
import net.liven.coreapi.utils.SpigotUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
public class CustomMenu {
    private final HashMap<Integer, MenuItem> menuItemMap = new HashMap<>();
    private final File menuFile;
    private String menuTitle;
    private int size;
    private List<String> openCommand;

    public CustomMenu(File file) {
        this.menuFile = file;
    }

    public void loadItems() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(menuFile);
        size = yml.getInt("size");
        menuTitle = ChatUtils.translateColors(yml.getString("gui-name"));
        openCommand = yml.getStringList("gui-command");

        Set<String> keys = yml.getConfigurationSection("items").getKeys(false);
        for (String key : keys) {
            ExecutableActionType actionType = ExecutableActionType.valueOf(yml.getString("items." + key + ".action-type"));

            int slot = yml.getInt("items." + key + ".slot");
            short data = (short) yml.getInt("items." + key + ".data", 0);
            String materialName = yml.getString("items." + key + ".material");
            Material material;
            int amount = yml.getInt("items." + key + ".amount");
            String name = yml.getString("items." + key + ".name", "Null");
            List<String> lore = yml.getStringList("items." + key + ".lore");
            ItemStack itemStack;

            if (materialName.equals("head")) {
                String value = yml.getString("items." + key + ".value");
                itemStack = SpigotUtils.getHead(value);
            } else {
                material = Material.valueOf(materialName);
                itemStack = new ItemStack(material, amount, data);
            }

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatUtils.translateColors(name));
            itemMeta.setLore(ChatUtils.translateColors(lore));
            itemStack.setItemMeta(itemMeta);
            ExecutableAction action;

            if(actionType == ExecutableActionType.COMMAND) {
                List<String> commands = yml.getStringList("items." + key + ".success-command");
                action = new CommandAction(commands);
            } else {
                action = new EmptyAction();
            }
            MenuItem menuItem = new MenuItem(itemStack, action);

            try {
                List<Integer> additionalSlots = yml.getIntegerList("items." + key + ".additional-slots");
                for (int i : additionalSlots) {
                    menuItemMap.put(i, menuItem);
                }
            } catch (Exception ignored) {}
            menuItemMap.put(slot, menuItem);
        }
    }



    public void populateInventory(Inventory upperInventory) {
        for(int slot : menuItemMap.keySet()) {
            upperInventory.setItem(slot, menuItemMap.get(slot).getItemStack());
        }
    }

    public MenuItem getClickedItem(int slot) {
        if (menuItemMap.containsKey(slot)) {
            return menuItemMap.get(slot);
        }
        return null;
    }
}
