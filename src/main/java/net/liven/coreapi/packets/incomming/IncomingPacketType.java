package net.liven.coreapi.packets.incomming;

public interface IncomingPacketType {

    default String getType(){
        return this.toString().toLowerCase();
    }

}
