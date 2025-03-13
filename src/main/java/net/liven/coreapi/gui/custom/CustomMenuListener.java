package net.liven.coreapi.gui.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class CustomMenuListener implements Listener {
    private final CustomMenuManager customMenuManager;

    public CustomMenuListener(CustomMenuManager customMenuManager) {
        this.customMenuManager = customMenuManager;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        Inventory upperInventory = e.getView().getTopInventory();
        Player player = (Player) e.getPlayer();
        if(upperInventory == null) return;

        CustomMenu customMenu = customMenuManager.getPlayerInventory(player);
        if(customMenu == null) return;
        customMenu.populateInventory(upperInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory upperInventory = e.getView().getTopInventory();
        Player player = (Player) e.getWhoClicked();
        if(upperInventory == null) return;
        CustomMenu customMenu = customMenuManager.getPlayerInventory(player);
        if(customMenu == null) return;
        MenuItem menuItem = customMenu.getClickedItem(e.getSlot());
        if (menuItem == null) return;
        menuItem.interact(player);
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Inventory upperInventory = e.getView().getTopInventory();
        Player player = (Player) e.getPlayer();
        if(upperInventory == null) return;
        CustomMenu customMenu = customMenuManager.getPlayerInventory(player);
        if(customMenu == null) return;
        customMenuManager.closeInventory(player);
    }
}
