package net.liven.coreapi;

import lombok.Getter;
import lombok.Setter;
import net.liven.coreapi.packets.incomming.IncomingPacketManager;
import net.liven.coreapi.server.Platform;
import net.liven.coreapi.server.bungee.BungeeConfig;
import net.liven.coreapi.server.bungee.BungeeWrappedServer;
import net.liven.coreapi.server.spigot.SpigotConfig;
import net.liven.coreapi.server.spigot.SpigotWrappedServer;
import net.liven.coreapi.server.wrappers.WrappedServer;
import net.liven.coreapi.server.wrappers.config.WrappedConfig;

@Getter
public class CoreAPIManager {
    private final Platform platform;
    private final WrappedServer wrappedServer;
    @Getter
    @Setter
    private static CoreAPIManager instance;
    private final Object plugin;
    private final IncomingPacketManager incomingPacketManager;

    public CoreAPIManager(Platform platform, Object plugin) {
        instance = this;
        this.plugin = plugin;
        this.platform = platform;
        if (platform == Platform.SPIGOT) {
            wrappedServer = new SpigotWrappedServer();
        } else {
            wrappedServer = new BungeeWrappedServer();
        }
        incomingPacketManager = new IncomingPacketManager();
    }

    public void init(){
        incomingPacketManager.registerPacketListener(this);
    }

    public WrappedConfig createWrappedConfig() {
        if (platform == Platform.SPIGOT) {
            return new SpigotConfig();
        } else {
            return new BungeeConfig();
        }
    }
}
