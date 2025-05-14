package net.liven.coreapi.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.packets.incomming.IncomingPacketType;
import net.liven.coreapi.packets.defaults.StringPacket;
import net.liven.coreapi.packets.listener.BukkitPacketListener;
import net.liven.coreapi.packets.listener.BungeePacketListener;
import net.liven.coreapi.packets.listener.PacketListener;
import net.liven.coreapi.packets.objects.PacketType;
import net.liven.coreapi.packets.outgoing.IPacketFace;
import net.liven.coreapi.packets.outgoing.PackerHolder;
import net.liven.coreapi.server.Platform;
import net.liven.coreapi.server.wrappers.WrappedPlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class PacketManager {
    private final HashMap<IncomingPacketType, IPacketFace> registeredPackets = new HashMap<>();
    private PacketListener packetListener;
    public HashMap<Integer, PackerHolder> awaitingPacketResponse = new HashMap<>();


    public void registerPacketListener(CoreAPIManager manager) {
        if(manager.getPlatform() == Platform.SPIGOT){
            packetListener = new BukkitPacketListener(this);
        } else if(manager.getPlatform() == Platform.BUNGEECORD){
            packetListener = new BungeePacketListener(this);
        }
        packetListener.registerChannels();
    }

    public void registerPacket(IncomingPacketType packetType, IPacketFace packetFace) {
        registeredPackets.put(packetType, packetFace);
    }


    public void processResponse(byte[] packet) {
        CoreAPIManager manager = CoreAPIManager.getInstance();
        Kryo kryo = manager.getKryo();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet);
             DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream)) {
            PacketType packetType = PacketType.valueOf(dataInputStream.readUTF());
            String playerId = dataInputStream.readUTF();
            WrappedPlayer wrappedPlayer = manager.getWrappedServer().getPlayer(UUID.fromString(playerId));
            if (wrappedPlayer == null) return;

            if (packetType == PacketType.DATA_REQUEST_PACKET) {
                Input input = new Input(dataInputStream);
                StringPacket receivedObj = kryo.readObject(input, StringPacket.class);
                input.close();
                PackerHolder packerHolder = awaitingPacketResponse.get(receivedObj.getId());
                packerHolder.onReceive(wrappedPlayer, receivedObj);
                awaitingPacketResponse.remove(receivedObj.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
