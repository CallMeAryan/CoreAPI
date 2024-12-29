package net.liven.coreAPI.core.command.execute;

import net.liven.coreAPI.core.command.CommandData;
import net.liven.coreAPI.core.messages.Message;
import net.liven.coreAPI.core.utils.ReflectionUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
    ICommand buildCommand(String name,String permission, Consumer<Player> consumer, Message message);
    ICommand buildTargetCommand(String name,String permission, BiConsumer<Player, Player> consumer,Message message);
    void sendMessage(Player player, Message coreMessage);
    ICommand buildStringCommand(String create, int size ,String permission, BiConsumer<Player, String[]> consumer, Message coreMessage);
    void setSuccess(boolean b);

    String getName();
    List<String> getAlias();
    String getPermission();
    CommandData<?,?,?> getArg(String name);

    default void register(Plugin instance){
        ReflectionUtils.addCommand(instance, new CommandWrapper(this));
    }

    String getDescription();

    String getUsageMessage();

    Collection<CommandData<?, ?, ?>> getCommands();
}
