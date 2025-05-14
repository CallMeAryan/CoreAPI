package net.liven.coreapi.packets.outgoing;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.packets.objects.PacketType;
import net.liven.coreapi.packets.objects.SerializablePacket;
import net.liven.coreapi.server.wrappers.WrappedPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IPacketFace extends SerializablePacket {
    PacketType getType();
    int getId();


    default void sendPacket(WrappedPlayer player, Class<?> packetClass) {
        Kryo kryo = CoreAPIManager.getInstance().getKryo();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
            dataOutputStream.writeUTF(getType().toString());
            dataOutputStream.writeUTF(player.getName());
            Output output = new Output(dataOutputStream);
            kryo.writeObject(output, packetClass.cast(this));
            output.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            player.sendPacket(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
