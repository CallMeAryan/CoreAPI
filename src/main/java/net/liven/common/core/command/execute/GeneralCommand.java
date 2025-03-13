package net.liven.common.core.command.execute;


import lombok.Getter;
import net.liven.common.core.command.CommandData;
import net.liven.common.core.command.args.ArgType;
import net.liven.common.core.messages.Message;
import net.liven.common.core.messages.Placeholder;
import net.liven.common.core.server.wrappers.WrappedPlayer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class GeneralCommand implements ICommand {
    private String name;
    private final List<String> alias = new ArrayList<>();
    private String description;
    private String usageMessage;
    private String permission;
    protected final HashMap<String, CommandData> commandMap = new HashMap<>();
    @Getter
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
    public ICommand buildCommand(String name, String permission, Consumer<WrappedPlayer> consumer, Message message) {
        CommandData commandData = new CommandData(name,0, consumer, permission, message, ArgType.PLAYER);
        commandMap.put(name, commandData);
        return this;
    }

    @Override
    public ICommand buildTargetCommand(String name,String permission, BiConsumer<WrappedPlayer, WrappedPlayer> consumer, Message message) {
        CommandData commandData = new CommandData(name,1, consumer, permission, message, ArgType.PLAYER);
        commandMap.put(name, commandData);
        return this;
    }

    @Override
    public void sendMessage(WrappedPlayer player, Message message, Placeholder... placeholders) {
        message.sendMessage(player, placeholders);
    }

    @Override
    public ICommand buildStringCommand(String name, int size, String permission, BiConsumer<WrappedPlayer, String[]> consumer, Message message) {
        CommandData commandData = new CommandData(name,size, consumer, permission, message, ArgType.STRING);
        commandMap.put(name, commandData);
        return this;
    }

    @Override
    public void setSuccess(boolean b) {
        success = b;
    }

    @Override
    public CommandData getArg(String name) {
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
    public Collection<CommandData> getCommands() {
        return commandMap.values();
    }


}
