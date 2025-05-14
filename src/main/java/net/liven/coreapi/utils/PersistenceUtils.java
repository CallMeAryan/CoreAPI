package net.liven.coreapi.utils;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class PersistenceUtils {

    public static byte[] toBytes(List<String> stringList) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(stringList);
        return bos.toByteArray();
    }

    public static List<String> fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (List<String>) ois.readObject();
    }


    public static String encodeList(List<String> list) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(list);
        }

        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    @SuppressWarnings("unchecked")
    public static List<String> decodeList(String base64String) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(base64String);

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (List<String>) objectInputStream.readObject();
        }
    }

}
