package net.liven.coreapi.server.spigot;


import net.liven.coreapi.command.execute.CommandWrapper;
import net.liven.coreapi.server.wrappers.WrappedCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class SpigotWrappedCommand extends BukkitCommand {
    private final CommandWrapper command;

    public SpigotWrappedCommand(CommandWrapper command) {
        super(command.getCommand().getName(), command.getCommand().getDescription(), command.getCommand().getUsageMessage(), command.getCommand().getAlias());
        this.command = command;
    }


    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        WrappedCommandSender wrappedCommandSender;
        if(sender instanceof Player){
            wrappedCommandSender = new SpigotPlayer((Player) sender);
        } else {
            wrappedCommandSender = new SpigotConsole();
        }
        return command.tabComplete(wrappedCommandSender, alias, args, super.tabComplete(sender, alias, args));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        WrappedCommandSender wrappedCommandSender;
        if(commandSender instanceof Player){
            wrappedCommandSender = new SpigotPlayer((Player) commandSender);
        } else {
            wrappedCommandSender = new SpigotConsole();
        }
        return command.execute(wrappedCommandSender, s, strings);
    }
}
