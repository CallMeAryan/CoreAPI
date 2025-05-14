package net.liven.coreapi.packets.outgoing;

import net.liven.coreapi.packets.objects.PacketType;
import net.liven.coreapi.server.wrappers.WrappedPlayer;

import java.util.function.BiConsumer;

public record PackerHolder(IPacketFace packetData, PacketType type, int packetId,
                           BiConsumer<WrappedPlayer, IPacketFace> receivedCallback) {

    public void onReceive(WrappedPlayer player, IPacketFace packetData) {
        receivedCallback.accept(player, packetData);
    }


}
