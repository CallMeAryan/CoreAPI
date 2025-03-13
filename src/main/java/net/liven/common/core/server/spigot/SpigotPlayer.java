package net.liven.common.core.server.spigot;

import net.liven.common.core.server.wrappers.WrappedPlayer;
import net.liven.common.core.server.wrappers.WrappedServer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayer implements WrappedPlayer {
    private final Player player;

    public SpigotPlayer(Player player) {
        this.player = player;
    }


    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void kickPlayer() {
        player.kickPlayer("");
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void closeInventory() {
        player.closeInventory();
    }

    @Override
    public void performCommand(String s) {
        player.performCommand(s);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
