package net.liven.coreapi.packets.outgoing;

import net.liven.coreapi.server.wrappers.WrappedPlayer;

public interface IPacketData {
    void sendPacket(WrappedPlayer player, String channel);
}
