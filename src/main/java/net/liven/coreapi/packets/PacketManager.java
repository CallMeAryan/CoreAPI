package net.liven.coreapi.packets;

import net.liven.coreapi.packets.outgoing.PacketObject;

import java.util.HashMap;

public class PacketManager {
    public HashMap<Integer, PacketObject> awaitingPacketResponse = new HashMap<>();


}
