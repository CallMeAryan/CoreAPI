package net.liven.common.core;

import lombok.Getter;
import lombok.Setter;
import net.liven.common.core.server.Platform;
import net.liven.common.core.server.bungee.BungeeConfig;
import net.liven.common.core.server.bungee.BungeeWrappedServer;
import net.liven.common.core.server.spigot.SpigotConfig;
import net.liven.common.core.server.spigot.SpigotWrappedServer;
import net.liven.common.core.server.wrappers.WrappedServer;
import net.liven.common.core.server.wrappers.config.WrappedConfig;

@Getter
public class CoreAPIManager {
    private final Platform platform;
    private final WrappedServer wrappedServer;

    @Getter
    @Setter
    private static CoreAPIManager instance;

    private final Object plugin;

    private CoreAPIManager(Platform platform, Object plugin) {
        this.plugin = plugin;
        this.platform = platform;
        if (platform == Platform.SPIGOT) {
            wrappedServer = new SpigotWrappedServer();
        } else {
            wrappedServer = new BungeeWrappedServer();
        }
    }


    public WrappedConfig createWrappedConfig() {
        if (platform == Platform.SPIGOT) {
            return new SpigotConfig();
        } else {
            return new BungeeConfig();
        }
    }
}
