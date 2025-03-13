package net.liven.coreapi.gui.custom;

import lombok.Getter;;
import net.liven.coreapi.gui.custom.action.ExecutableAction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


@Getter
public class MenuItem {
    private final ItemStack itemStack;
    private final ExecutableAction executableAction;

    public MenuItem(ItemStack itemStack, ExecutableAction executableAction) {
        this.itemStack = itemStack;
        this.executableAction = executableAction;
    }


    public void interact(Player player) {
        executableAction.execute(player);
    }
}
