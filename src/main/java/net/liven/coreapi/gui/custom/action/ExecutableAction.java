package net.liven.coreapi.gui.custom.action;

import org.bukkit.entity.Player;

public interface ExecutableAction {
    ExecuteStatus execute(Player player, String... args);
}
