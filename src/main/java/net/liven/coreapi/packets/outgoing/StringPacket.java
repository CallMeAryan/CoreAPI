package net.liven.coreapi.packets.outgoing;

import net.liven.coreapi.server.wrappers.WrappedPlayer;

public class StringPacket implements IPacketData {
    private final String data;
    public StringPacket(String date) {
        this.data = date;
    }

    @Override
    public void sendPacket(WrappedPlayer player, String channel) {
        player.sendPacket(data.getBytes());
    }
}
