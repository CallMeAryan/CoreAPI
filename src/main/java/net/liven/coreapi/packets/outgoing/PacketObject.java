package net.liven.coreapi.packets.outgoing;

import net.liven.coreapi.packets.PacketType;
import net.liven.coreapi.server.wrappers.WrappedPlayer;

import java.util.function.BiConsumer;

public record PacketObject(IPacketData packetData, PacketType type, int packetId,
                           BiConsumer<WrappedPlayer, PacketObject> receivedCallback) {

    public void onReceive(WrappedPlayer player, PacketObject packet) {
        receivedCallback.accept(player, packet);
    }


}
