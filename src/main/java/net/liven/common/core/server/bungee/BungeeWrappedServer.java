package net.liven.common.core.server.bungee;

import net.liven.common.core.CoreAPIManager;
import net.liven.common.core.command.execute.CommandWrapper;
import net.liven.common.core.server.CommandSenderType;
import net.liven.common.core.server.wrappers.WrappedPlayer;
import net.liven.common.core.server.wrappers.WrappedServer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

public class BungeeWrappedServer implements WrappedServer {
    @Override
    public WrappedPlayer getPlayer(String name) {
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(name);
        if (p != null) {
            return new BungeePlayer(p);
        }
        return null;
    }

    @Override
    public WrappedPlayer getPlayer(UUID uuid) {
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);
        if (p != null) {
            return new BungeePlayer(p);
        }
        return null;
    }

    @Override
    public void addCommand(CommandWrapper command) {
        ((Plugin) getPlugin()).getProxy().getPluginManager().registerCommand((Plugin) CoreAPIManager.getInstance().getPlugin(), new BungeeWrappedCommand(command));
    }

    @Override
    public Object getPlugin() {
        return CoreAPIManager.getInstance().getPlugin();
    }

    @Override
    public void dispatchCommand(CommandSenderType commandSender, WrappedPlayer player, String command) {
        if (CommandSenderType.CONSOLE == commandSender) {
            ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
        } else {
            ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(player.getUniqueId());
            proxiedPlayer.chat(command);
        }
    }
}
