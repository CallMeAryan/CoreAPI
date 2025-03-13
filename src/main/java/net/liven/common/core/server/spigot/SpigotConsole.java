package net.liven.common.core.server.spigot;

import net.liven.common.core.server.wrappers.WrappedCommandSender;
import org.bukkit.Bukkit;

public class SpigotConsole implements WrappedCommandSender {
    @Override
    public void sendMessage(String message) {
        Bukkit.getLogger().info(message);
    }
}
