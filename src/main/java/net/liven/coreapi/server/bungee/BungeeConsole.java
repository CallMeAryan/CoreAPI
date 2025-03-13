package net.liven.coreapi.server.bungee;

import net.liven.coreapi.server.wrappers.WrappedCommandSender;
import net.md_5.bungee.api.ProxyServer;

public class BungeeConsole implements WrappedCommandSender {
    @Override
    public void sendMessage(String message) {
        ProxyServer.getInstance().getLogger().info(message);
    }
}
