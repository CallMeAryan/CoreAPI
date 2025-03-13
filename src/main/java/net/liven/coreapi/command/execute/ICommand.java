package net.liven.coreapi.command.execute;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.messages.IMessageFace;
import net.liven.coreapi.server.wrappers.WrappedPlayer;
import net.liven.coreapi.command.CommandData;
import net.liven.coreapi.placeholder.Placeholder;

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
    ICommand buildCommand(String name,String permission, Consumer<WrappedPlayer> consumer, IMessageFace message);
    ICommand buildTargetCommand(String name,String permission, BiConsumer<WrappedPlayer, WrappedPlayer> consumer, IMessageFace message);


    void sendMessage(WrappedPlayer WrappedPlayer, IMessageFace Message, Placeholder... placeholders);
    ICommand buildStringCommand(String create, int size ,String permission, BiConsumer<WrappedPlayer, String[]> consumer, IMessageFace coreMessage);
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
