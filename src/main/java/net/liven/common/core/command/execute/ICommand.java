package net.liven.common.core.command.execute;

import net.liven.common.core.CoreAPIManager;
import net.liven.common.core.server.wrappers.WrappedPlayer;
import net.liven.common.core.command.CommandData;
import net.liven.common.core.messages.Message;
import net.liven.common.core.messages.Placeholder;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ICommand {

    ICommand name(String name);
    ICommand alias(String... alias);
    ICommand description(String description);
    ICommand usageMessage(String usageMessage);
    ICommand permission(String permission);
    ICommand buildCommand(String name,String permission, Consumer<WrappedPlayer> consumer, Message message);
    ICommand buildTargetCommand(String name,String permission, BiConsumer<WrappedPlayer, WrappedPlayer> consumer,Message message);


    void sendMessage(WrappedPlayer WrappedPlayer, Message Message, Placeholder... placeholders);
    ICommand buildStringCommand(String create, int size ,String permission, BiConsumer<WrappedPlayer, String[]> consumer, Message coreMessage);
    void setSuccess(boolean b);

    boolean isSuccess();

    String getName();
    List<String> getAlias();
    String getPermission();
    CommandData getArg(String name);

    default void register(CoreAPIManager apiManager) {
        apiManager.getWrappedServer().addCommand(new CommandWrapper(this));
    }

    String getDescription();

    String getUsageMessage();

    Collection<CommandData> getCommands();
}
