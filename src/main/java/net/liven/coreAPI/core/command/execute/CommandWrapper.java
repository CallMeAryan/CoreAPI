package net.liven.coreAPI.core.command.execute;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandWrapper extends BukkitCommand {
    private final ICommand iCommand;

    protected CommandWrapper(ICommand iCommand) {
        super(iCommand.getName(), iCommand.getDescription(), iCommand.getUsageMessage(), iCommand.getAlias());
        this.iCommand = iCommand;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            iCommand.getCommands().forEach(commandData -> {
                if(commandData.args() == strings.length) {
                    if (commandData.name().equalsIgnoreCase(strings[0])) {


                    }
                }
            });
        }


        return true;
    }


    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return super.tabComplete(sender, alias, args);
    }
}
