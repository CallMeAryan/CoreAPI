package net.liven.coreapi.packets.defaults;

import net.liven.coreapi.packets.objects.PacketType;
import net.liven.coreapi.packets.outgoing.IPacketFace;

public class ActionPacket implements IPacketFace {
    @Override
    public PacketType getType() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }
}
