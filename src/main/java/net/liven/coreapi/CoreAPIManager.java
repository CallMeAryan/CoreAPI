package net.liven.coreapi;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Getter;
import lombok.Setter;
import net.liven.coreapi.messages.IMessageFace;
import net.liven.coreapi.messages.defaults.Messages;
import net.liven.coreapi.packets.defaults.ActionPacket;
import net.liven.coreapi.packets.defaults.DefaultPacketTypes;
import net.liven.coreapi.packets.defaults.StringPacket;
import net.liven.coreapi.packets.incomming.IncomingPacketType;
import net.liven.coreapi.packets.objects.SerializablePacket;
import net.liven.coreapi.packets.PacketManager;
import net.liven.coreapi.packets.outgoing.IPacketFace;
import net.liven.coreapi.server.Platform;
import net.liven.coreapi.server.bungee.BungeeConfig;
import net.liven.coreapi.server.bungee.BungeeWrappedServer;
import net.liven.coreapi.server.spigot.SpigotConfig;
import net.liven.coreapi.server.spigot.SpigotWrappedServer;
import net.liven.coreapi.server.wrappers.WrappedServer;
import net.liven.coreapi.server.wrappers.config.WrappedConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CoreAPIManager {
    private final Platform platform;
    private final WrappedServer wrappedServer;
    @Getter
    @Setter
    private static CoreAPIManager instance;
    private final Object plugin;
    private final PacketManager packetManager;
    private final Kryo kryo;
    private final List<Class<? extends IMessageFace>> registeredMessages = new ArrayList<>();

    @Getter
    private final List<Class<? extends SerializablePacket>> registeredSerializablePackets = new ArrayList<>();

    public CoreAPIManager(Platform platform, Object plugin) {
        instance = this;
        this.plugin = plugin;
        this.platform = platform;
        if (platform == Platform.SPIGOT) {
            wrappedServer = new SpigotWrappedServer();
        } else {
            wrappedServer = new BungeeWrappedServer();
        }
        packetManager = new PacketManager();
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
    }

    public void init(){
        packetManager.registerPacketListener(this);
        for (Class<? extends IMessageFace> messageClass : registeredMessages) {
            try {
                IMessageFace.generateConfig(messageClass, wrappedServer.getDataFolder());
            } catch (IOException e) {
                wrappedServer.getLogger().warn("Failed to generate config for message class " + messageClass.getSimpleName());
            }
        }
        for (Class<? extends SerializablePacket> serializablePacketClass : registeredSerializablePackets) {
            kryo.register(serializablePacketClass);
        }



        registerPacket(DefaultPacketTypes.PLAYER_KICK_PACKET, new ActionPacket());
        registerPacket(DefaultPacketTypes.SERVER_SWITCH_PACKET, new ActionPacket());
        registerPacket(DefaultPacketTypes.STRING_DATA_PACKET, new ActionPacket());
    }

    private void registerPacket(IncomingPacketType packetType, IPacketFace iPacket) {
        registeredSerializablePackets.add(iPacket.getClass());
        packetManager.registerPacket(packetType, iPacket);
    }

    public void registerMessage(Class<? extends Messages> messageClass) {
        registeredMessages.add(messageClass);
    }

    public WrappedConfig createWrappedConfig() {
        if (platform == Platform.SPIGOT) {
            return new SpigotConfig();
        } else {
            return new BungeeConfig();
        }
    }
}
