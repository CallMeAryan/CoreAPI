package net.liven.coreapi.gui.custom.action;

import lombok.Getter;
import lombok.Setter;
import net.liven.coreapi.placeholder.PlaceholderManager;
import net.liven.coreapi.server.spigot.SpigotPlayer;
import net.liven.coreapi.utils.Base64Utils;
import net.liven.coreapi.utils.CoreUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

@Setter
@Getter
public class CommandAction implements ExecutableAction {

    private final List<String> command;

    public CommandAction(String actionData) {
        if (actionData.isEmpty()) {
            throw new NullPointerException("Action Data is empty");
        }
        this.command = unzipCommand(actionData);
    }

    public CommandAction(List<String> command) {
        this.command = command;
    }

    @Override
    public ExecuteStatus execute(Player player, String... args) {
        CommandStatus status = executeCommand(player);
        if (status == CommandStatus.SUCCESS) {
            return ExecuteStatus.SUCCESS;
        } else {
            return ExecuteStatus.FAILED;
        }
    }

    private List<String> unzipCommand(String datum) {
        try {
            return Base64Utils.decodeList(datum);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String zipCommand() {
        try {
            return Base64Utils.encodeList(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CommandStatus executeCommand(Player player) {
        if (getCommand().isEmpty()) return CommandStatus.FAILED;

        for (String command : getCommand()) {
            String executable = PlaceholderManager.getInstance().parse(command.replace("<player>", player.getName()), new SpigotPlayer(player));
            CoreUtils.performAction(new SpigotPlayer(player), executable);
        }
        return CommandStatus.SUCCESS;
    }

    public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : command) {
            stringBuilder.append(s).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return zipCommand();
    }

    public enum CommandStatus {
        SUCCESS,
        FAILED
    }
}
