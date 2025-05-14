package net.liven.coreapi.database.mongo.data.types;

import lombok.Getter;
import net.liven.coreapi.database.mongo.data.MongoKey;
import net.liven.coreapi.database.mongo.data.MongoSerializable;
import org.bson.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class SerializableList<K> extends ArrayList<K> implements MongoSerializable {
    private boolean needed = false;

    public SerializableList() {
        super();
    }




    public List<String> getSerializable() {
        List<String> serializableList = new ArrayList<>();
        for (K element : this) {
            if (element == null) continue;
            if (element instanceof Enum<?>) {
                serializableList.add(((Enum<?>) element).name());
            } else {
                serializableList.add(element.toString());
            }
        }
        return serializableList;
    }

    @Override
    public Document toDocument() {
        Document valueDoc = new Document();
        if(!this.isEmpty()) {
            Object firstObj = this.get(0);
            if(firstObj instanceof Enum<?>) {
                valueDoc.put("type", "String");
                valueDoc.put("value", getSerializable());
            } else {
                String type = firstObj.getClass().getSimpleName();
                valueDoc.put("type", type);
                valueDoc.put("value", getSerializable());
            }
            return valueDoc;
        }
        return null;
    }

    public static <T extends Enum<T>> SerializableList<T> convertEnumList(SerializableList<String> stringSerializableList, Class<T> enumClass) {
        SerializableList<T> enumList = new SerializableList<>();
        for (String enumName : stringSerializableList) {
            try {
                T enumValue = Enum.valueOf(enumClass, enumName);
                enumList.add(enumValue);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid enum name: " + enumName, e);
            }
        }
        return enumList;
    }

    public static SerializableList<?> fromDocument(Document doc) {
        SerializableList<?> list;
        if (doc == null) return null;

        String type = doc.getString("type");
        List<String> rawData = doc.getList("value", String.class);
        if (rawData == null) return null;

        if(type.equalsIgnoreCase("String")) {
           SerializableList<String> strings  = new SerializableList<>();
            for (String raw : rawData) {
                if (raw == null) continue;
                try {
                    strings.add(raw);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to deserialize element: " + raw, e);
                }
            }
            list = strings;
        } else if (type.equalsIgnoreCase("UUID")) {
            SerializableList<UUID> uuids = new SerializableList<>();
            for (String raw : rawData) {
                if (raw == null) continue;
                try {
                    uuids.add(UUID.fromString(raw));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to deserialize element: " + raw, e);
                }
            }
            list = uuids;
        } else if (type.equalsIgnoreCase("Integer")) {
            SerializableList<Integer> integers = new SerializableList<>();
            for (String raw : rawData) {
                if (raw == null) continue;
                try {
                    integers.add(Integer.valueOf(raw));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to deserialize element: " + raw, e);
                }
            }
            list = integers;
        } else if (type.equalsIgnoreCase("Double")) {
            SerializableList<Double> doubles = new SerializableList<>();
            for (String raw : rawData) {
                if (raw == null) continue;
                try {
                    doubles.add(Double.valueOf(raw));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to deserialize element: " + raw, e);
                }
            }
            list = doubles;
        } else if (type.equalsIgnoreCase("Float")) {
            SerializableList<Float> floats = new SerializableList<>();
            for (String raw : rawData) {
                if (raw == null) continue;
                try {
                    floats.add(Float.valueOf(raw));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to deserialize element: " + raw, e);
                }
            }
            list = floats;
        } else if (type.equalsIgnoreCase("Long")) {
            SerializableList<Long> longs = new SerializableList<Long>();
            for (String raw : rawData) {
                if (raw == null) continue;
                try {
                    longs.add(Long.valueOf(raw));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to deserialize element: " + raw, e);
                }
            }
            list = longs;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
        return list;
    }


    public static <L> SerializableList<L> getBaseList(Class<L> type, List<String> rawData) {
        SerializableList<L> list = new SerializableList<>();
        if (rawData == null) return list;

        for (String data : rawData) {
            if (data == null) continue;

            try {
                Object value;

                if (type.equals(String.class)) {
                    value = data;
                } else if (type.equals(UUID.class)) {
                    value = UUID.fromString(data);
                } else if (type.equals(Integer.class)) {
                    value = Integer.valueOf(data);
                } else if (type.equals(Long.class)) {
                    value = Long.valueOf(data);
                } else if (type.equals(Double.class)) {
                    value = Double.valueOf(data);
                } else if (type.equals(Float.class)) {
                    value = Float.valueOf(data);
                } else if (type.isEnum()) {
                    value = Enum.valueOf((Class<Enum>) type.asSubclass(Enum.class), data);
                } else {
                    throw new IllegalArgumentException("Unsupported type: " + type.getName());
                }

                list.add((L) value);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to deserialize element: " + data, e);
            }
        }

        return list;
    }

    public static <L> SerializableList<L> fromDocument(Document document, MongoKey field, Class<L> type) {
        List<String> rawData = document.getList(field.name(), String.class);
        return getBaseList(type, rawData);
    }

    public static boolean isEmptyOrNull(List<?> list) {
        return list == null || list.isEmpty();
    }

    @Override
    public MongoSerializable required() {
        needed = true;
        return this;
    }

    @Override
    public String toString() {
        return "SerializableList{" +
                "size=" + size() +
                ", elements=" + super.toString() +
                '}';
    }
}
