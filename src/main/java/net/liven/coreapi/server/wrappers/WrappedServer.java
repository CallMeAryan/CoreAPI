package net.liven.coreapi.server.wrappers;

import net.liven.coreapi.command.execute.CommandWrapper;
import net.liven.coreapi.server.CommandSenderType;

import java.io.File;
import java.util.UUID;

public interface WrappedServer {
    WrappedPlayer getPlayer(String name);
    WrappedPlayer getPlayer(UUID uuid);
    void addCommand(CommandWrapper command);
    Object getPlugin();
    void dispatchCommand(CommandSenderType commandSender, WrappedPlayer player, String command);
    File getDataFolder();
}
