package net.liven.coreapi.gui.custom.action;

import org.bukkit.entity.Player;

public class EmptyAction implements  ExecutableAction {
    @Override
    public ExecuteStatus execute(Player player, String... args) {
        return ExecuteStatus.SUCCESS;
    }
}
