package net.liven.coreapi.packets.outgoing;

import net.liven.coreapi.server.wrappers.WrappedPlayer;
import net.liven.coreapi.utils.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.List;

public class CustomDataPayload {
    private final List<Object> data = new ArrayList<>();


    public void addStringList(List<String> stringList) {
        data.add(stringList);  // Store the entire list directly
    }

    public void addString(String string) {
        data.add(string);
    }

    public void addInt(int integer) {
        data.add(integer);
    }

    private void addLong(long longValue) {
        data.add(longValue);
    }

    private void addFloat(float floatValue) {
        data.add(floatValue);
    }

    private void addDouble(double doubleValue) {
        data.add(doubleValue);
    }

    private void addBoolean(boolean booleanValue) {
        data.add(booleanValue);
    }

    public void sendCustomPayload(WrappedPlayer player) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {

            for (Object obj : data) {
                if (obj instanceof String) {
                    dataOutputStream.writeUTF((String) obj);
                } else if (obj instanceof Integer) {
                    dataOutputStream.writeInt((Integer) obj);
                } else if (obj instanceof Long) {
                    dataOutputStream.writeLong((Long) obj);
                } else if (obj instanceof Float) {
                    dataOutputStream.writeFloat((Float) obj);
                } else if (obj instanceof Double) {
                    dataOutputStream.writeDouble((Double) obj);
                } else if (obj instanceof Boolean) {
                    dataOutputStream.writeBoolean((Boolean) obj);
                } else if (obj instanceof List) {
                    // Write list as a serialized JSON or handle with safer delimiters
                    List<String> list = (List<String>) obj;
                    try {
                        dataOutputStream.writeUTF(Base64Utils.encodeList(list));
                    } catch (UTFDataFormatException e) {
                        return;
                    }

                }
            }
            byte[] payload = byteArrayOutputStream.toByteArray();

            player.sendPacket(payload);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
