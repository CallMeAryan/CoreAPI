package net.liven.common.core.server.bungee;

import net.liven.common.core.server.wrappers.WrappedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePlayer implements WrappedPlayer {
    private final ProxiedPlayer proxiedPlayer;

    public BungeePlayer(ProxiedPlayer proxiedPlayer) {
        this.proxiedPlayer = proxiedPlayer;
    }

    @Override
    public UUID getUniqueId() {
        return proxiedPlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return proxiedPlayer.getName();
    }

    @Override
    public void kickPlayer() {
        proxiedPlayer.disconnect();
    }

    @Override
    public boolean hasPermission(String permission) {
        return proxiedPlayer.hasPermission(permission);
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public void performCommand(String s) {
        proxiedPlayer.chat(s);
    }

    @Override
    public void sendMessage(String message) {
        proxiedPlayer.sendMessage(message);
    }
}
