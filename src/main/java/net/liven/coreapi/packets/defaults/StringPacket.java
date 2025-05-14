package net.liven.coreapi.packets.defaults;

import lombok.Getter;
import net.liven.coreapi.packets.objects.PacketType;
import net.liven.coreapi.packets.outgoing.IPacketFace;

@Getter
public class StringPacket implements IPacketFace {
    private final int id;
    private final String data;

    public StringPacket(int id, String data) {
        this.id = id;
        this.data = data;
    }


    @Override
    public PacketType getType() {
        return PacketType.DATA_REQUEST_PACKET;
    }


}
