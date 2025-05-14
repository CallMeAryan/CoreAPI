package net.liven.coreapi.server.spigot;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.server.WrappedLogger;
import org.bukkit.plugin.Plugin;

public class SpigotLogger implements WrappedLogger {
    private final Plugin plugin;

    public SpigotLogger() {
        this.plugin = (Plugin) CoreAPIManager.getInstance().getPlugin();
    }

    @Override
    public void warn(String message) {
        plugin.getLogger().warning(message);
    }

    @Override
    public void info(String message) {
        plugin.getLogger().info(message);
    }

    @Override
    public void error(String message) {
        plugin.getLogger().severe(message);
    }

    @Override
    public void debug(String message) {
        plugin.getLogger().info("[DEBUG] " + message);
    }
}
