package net.liven.coreapi.gui.custom;

import lombok.Getter;
import net.liven.coreapi.messages.defaults.Messages;
import net.liven.coreapi.placeholder.Placeholder;
import net.liven.coreapi.server.spigot.SpigotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class CustomMenuManager {
    private final HashMap<String, CustomMenu> customMenuMap = new HashMap<>();
    private final HashMap<Player, CustomMenu> playerInventoryMap = new HashMap<>();
    private final CustomMenuListener customMenuListener = new CustomMenuListener(this);

    public CustomMenu getCustomMenu(String menuName){
        return customMenuMap.get(menuName);
    }

    public void loadMenus(File dateFolder){
        File menuDir = new File(dateFolder, "menus");
        if (!menuDir.exists() && menuDir.mkdirs()) {
            System.out.println("Created directory: " + menuDir.getAbsolutePath());
        }
        List<File> ymlFiles = Arrays.stream(Objects.requireNonNull(menuDir.listFiles((d, name) -> name.endsWith(".yml"))))
                .collect(Collectors.toList());

        if (!ymlFiles.isEmpty()) {
            ymlFiles.forEach(ymlFile -> {
                CustomMenu customMenu = new CustomMenu(ymlFile);
                customMenu.loadItems();
                customMenuMap.put(ymlFile.getName().replace(".yml", ""), customMenu);
            });
        }
    }

    public void openMenu(Player player, String menuName){
        CustomMenu customMenu = getCustomMenu(menuName);
        if (customMenu != null) {
            playerInventoryMap.put(player, customMenu);
            Inventory inventory = Bukkit.createInventory(null, customMenu.getSize(), customMenu.getMenuTitle());
            player.openInventory(inventory);
        } else {
            Messages.MENU_NOT_FOUND.sendMessage(new SpigotPlayer(player), new Placeholder("menu", menuName));
        }
    }

    public CustomMenu getPlayerInventory(Player player){
        return playerInventoryMap.get(player);
    }

    public void closeInventory(Player player){
        playerInventoryMap.remove(player);
    }

    @Getter
    public static class OpenMenu{
        private final CustomMenu customMenu;
        private final Player player;

        public OpenMenu(CustomMenu customMenu, Player player) {
            this.customMenu = customMenu;
            this.player = player;
        }
    }

}
