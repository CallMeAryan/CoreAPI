package net.liven.coreapi.server.bungee;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.server.WrappedLogger;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeLogger implements WrappedLogger {
    private final Plugin plugin;

    public BungeeLogger() {
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
