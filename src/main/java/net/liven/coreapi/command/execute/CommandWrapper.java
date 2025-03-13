package net.liven.coreapi.command.execute;


import lombok.Getter;
import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.messages.defaults.Messages;
import net.liven.coreapi.command.CommandData;
import net.liven.coreapi.command.args.ArgType;
import net.liven.coreapi.server.wrappers.WrappedCommandSender;
import net.liven.coreapi.server.wrappers.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandWrapper {
    private final ICommand command;

    public CommandWrapper(ICommand command) {
        this.command = command;
    }


    public boolean execute(@NotNull WrappedCommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof WrappedPlayer WrappedPlayer) {
            command.getCommands().forEach(commandData -> {
               if (commandData.args() == strings.length && commandData.name().equalsIgnoreCase(strings[0])) {
                   processCommand(WrappedPlayer, commandData, strings);
                }
            });
        }
        return true;
    }

    private void processCommand(WrappedPlayer WrappedPlayer, CommandData commandData, String[] strings) {
        if (strings.length == 1) {
            handleSingleArgCommand(WrappedPlayer, commandData);
        } else if (strings.length == 2) {
            handleTwoArgCommand(WrappedPlayer, commandData, strings);
        }
    }

    private void handleSingleArgCommand(WrappedPlayer WrappedPlayer, CommandData commandData) {
        command.setSuccess(true);
        if (commandData.type().equals(ArgType.PLAYER)) {
            executeIfPermitted(WrappedPlayer, commandData, () -> {
                commandData.execute(WrappedPlayer, null, null);
            });
        }  else if (commandData.type().equals(ArgType.STRING)) {
            executeIfPermitted(WrappedPlayer, commandData, () -> commandData.execute(WrappedPlayer, null, null));
        }

        if (command.isSuccess()) {
            command.sendMessage(WrappedPlayer, commandData.successMessage());
        }
    }

    private void handleTwoArgCommand(WrappedPlayer WrappedPlayer, CommandData commandData, String[] strings) {
        command.setSuccess(true);

        if (commandData.type().equals(ArgType.PLAYER)) {
            WrappedPlayer target = CoreAPIManager.getInstance().getWrappedServer().getPlayer(strings[1]);
            executeIfPermitted(WrappedPlayer, commandData, () -> commandData.execute(WrappedPlayer, target, null));
        } else if (commandData.type().equals(ArgType.STRING)) {
            String[] data = extractArguments(strings);
            executeIfPermitted(WrappedPlayer, commandData, () -> commandData.execute(WrappedPlayer, data, null));
        }
        if (command.isSuccess()) {
            command.sendMessage(WrappedPlayer, commandData.successMessage());
        }
    }


    private void executeIfPermitted(WrappedPlayer WrappedPlayer, CommandData commandData, Runnable action) {
        if (WrappedPlayer.hasPermission(commandData.permission())) {
            command.setSuccess(true);
            action.run();
        } else {
            command.setSuccess(false);
            command.sendMessage(WrappedPlayer, Messages.NO_PERMISSION);
        }
    }

    private String[] extractArguments(String[] strings) {
        String[] data = new String[strings.length - 1];
        System.arraycopy(strings, 1, data, 0, strings.length - 1);
        return data;
    }



    public @NotNull List<String> tabComplete(@NotNull WrappedCommandSender sender, @NotNull String alias, @NotNull String[] args, List<String> superReturn) throws IllegalArgumentException {
        List<String> argumentList = new ArrayList<>();
        if(args.length == 1) {
            command.getCommands().forEach(commandData -> argumentList.add(commandData.name()));
            return argumentList;
        }
        return superReturn;
    }
}
