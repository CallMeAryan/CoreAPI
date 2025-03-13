package net.liven.common.core.server.wrappers;

import net.liven.common.core.command.execute.CommandWrapper;
import net.liven.common.core.server.CommandSenderType;

import java.util.UUID;

public interface WrappedServer {
    WrappedPlayer getPlayer(String name);
    WrappedPlayer getPlayer(UUID uuid);
    void addCommand(CommandWrapper command);
    Object getPlugin();
    void dispatchCommand(CommandSenderType commandSender, WrappedPlayer player, String command);
}
