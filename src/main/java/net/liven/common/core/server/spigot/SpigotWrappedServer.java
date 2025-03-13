package net.liven.common.core.server.spigot;

import net.liven.common.core.CoreAPIManager;
import net.liven.common.core.command.execute.CommandWrapper;
import net.liven.common.core.server.CommandSender;
import net.liven.common.core.server.CommandSenderType;
import net.liven.common.core.server.wrappers.WrappedPlayer;
import net.liven.common.core.server.wrappers.WrappedServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class SpigotWrappedServer implements WrappedServer {
    @Override
    public WrappedPlayer getPlayer(String name) {
        Player p = Bukkit.getPlayer(name);
        if (p != null) {
            return new SpigotPlayer(p);
        }
        return null;
    }

    @Override
    public WrappedPlayer getPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
            return new SpigotPlayer(p);
        }
        return null;
    }

    @Override
    public void addCommand(CommandWrapper command) {
        ReflectionUtils.addCommand((Plugin) getPlugin(), new SpigotWrappedCommand(command));
    }

    @Override
    public Object getPlugin() {
        return CoreAPIManager.getInstance().getPlugin();
    }

    @Override
    public void dispatchCommand(CommandSenderType commandSender, WrappedPlayer player, String command) {
        if (commandSender == CommandSenderType.CONSOLE) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } else {
            Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
            bukkitPlayer.performCommand(command);
        }
    }




}
