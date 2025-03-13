package net.liven.coreapi.server.spigot;

import net.liven.coreapi.server.wrappers.WrappedCommandSender;
import org.bukkit.Bukkit;

public class SpigotConsole implements WrappedCommandSender {
    @Override
    public void sendMessage(String message) {
        Bukkit.getLogger().info(message);
    }
}
