package net.liven.coreapi.packets;

public interface PacketType {
    default boolean isSamePacket(PacketType packetType){
        return this.equals(packetType);
    }

    String packetID();

}
