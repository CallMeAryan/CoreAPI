package net.liven.coreapi.packets.incomming;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.packets.listener.BukkitPacketListener;
import net.liven.coreapi.packets.listener.BungeePacketListener;
import net.liven.coreapi.packets.listener.PacketListener;
import net.liven.coreapi.server.Platform;

import java.util.HashMap;

public class IncomingPacketManager {
    private HashMap<IncomingPacketType, DataRequestPacket> registeredPackets = new HashMap<>();
    private PacketListener packetListener;



    public void registerPacketListener(CoreAPIManager manager) {
        if(manager.getPlatform() == Platform.SPIGOT){
            packetListener = new BukkitPacketListener(this);
        } else if(manager.getPlatform() == Platform.BUNGEECORD){
            packetListener = new BungeePacketListener(this);
        }
        packetListener.registerChannels();
    }


}
