package net.liven.common.core.server.bungee;

import net.liven.common.core.command.execute.CommandWrapper;
import net.liven.common.core.server.wrappers.WrappedCommandSender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class BungeeWrappedCommand extends Command implements TabExecutor {
    private final CommandWrapper command;

    public BungeeWrappedCommand(CommandWrapper command) {
        super(command.getCommand().getName(), command.getCommand().getPermission(), command.getCommand().getAlias().toArray(new String[0]));
        this.command = command;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        WrappedCommandSender wrappedCommandSender;
        if (commandSender instanceof ProxiedPlayer) {
            wrappedCommandSender = new BungeePlayer((ProxiedPlayer) commandSender);
        } else {
            wrappedCommandSender = new BungeeConsole();
        }
        command.execute(wrappedCommandSender, "", strings);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        WrappedCommandSender wrappedCommandSender;
        if (commandSender instanceof ProxiedPlayer) {
            wrappedCommandSender = new BungeePlayer((ProxiedPlayer) commandSender);
        } else {
            wrappedCommandSender = new BungeeConsole();
        }
        return command.tabComplete(wrappedCommandSender, "", strings, new ArrayList<>());
    }
}
