package net.liven.coreAPI.core.command.execute;

import lombok.Getter;
import lombok.Setter;
import net.liven.coreAPI.core.command.CommandData;
import net.liven.coreAPI.core.messages.Message;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class GeneralCommand implements ICommand {
    private String name;
    private List<String> alias;
    private String description;
    private String usageMessage;
    private String permission;
    protected final HashMap<String, CommandData<?,?,?>> commandMap = new HashMap<>();
    @Setter
    private boolean success = false;


    @Override
    public ICommand name(String name) {
        this.name = name;
        return this;
    }
    
    
    

    @Override
    public ICommand alias(String... alias) {
        Collections.addAll(this.alias, alias);
        return this;
    }

    @Override
    public ICommand description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public ICommand usageMessage(String usageMessage) {
        this.usageMessage = usageMessage;
        return this;
    }

    @Override
    public ICommand permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public ICommand buildCommand(String name,String permission, Consumer<Player> consumer, Message message) {
        CommandData<Player, ?, ?> commandData = new CommandData<>(name,0, consumer, permission, message);
        commandMap.put(name, commandData);
        return this;
    }

    @Override
    public ICommand buildTargetCommand(String name,String permission, BiConsumer<Player, Player> consumer, Message message) {
        CommandData<Player, Player, ?> commandData = new CommandData<>(name,1, consumer, permission, message);
        commandMap.put(name, commandData);
        return this;
    }

    @Override
    public void sendMessage(Player player, Message coreMessage) {

    }

    @Override
    public ICommand buildStringCommand(String create, int size, String permission, BiConsumer<Player, String[]> consumer, Message message) {
        CommandData<Player, String[], ?> commandData = new CommandData<>(create,size, consumer, permission, message);
        commandMap.put(create, commandData);
        return this;
    }

    @Override
    public CommandData<?, ?, ?> getArg(String name) {
        return commandMap.get(name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUsageMessage() {
        return usageMessage;
    }

    @Override
    public Collection<CommandData<?, ?, ?>> getCommands() {
        return commandMap.values();
    }



}
