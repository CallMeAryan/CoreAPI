package net.liven.coreapi.database.mongo.data.types;

import net.liven.coreapi.database.mongo.data.MongoSerializable;
import org.bson.Document;

import java.io.Serializable;
import java.util.*;

public class SerializableHashMap<K, V> implements Serializable, MongoSerializable {
    private final HashMap<K, V> internalMap = new HashMap<>();
    private boolean needed = false;

    public void put(K key, V value) {
        internalMap.put(key, value);
    }

    public V get(K key) {
        return internalMap.get(key);
    }

    public boolean containsKey(K key) {
        return internalMap.containsKey(key);
    }

    public void remove(K key) {
        internalMap.remove(key);
    }

    public Map<K, V> getRawMap() {
        return internalMap;
    }

    public int size() {
        return internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    public void clear() {
        internalMap.clear();
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();

        if (!internalMap.isEmpty()) {
            for (Map.Entry<K, V> entry : internalMap.entrySet()) {
                String keyStr = entry.getKey().toString();
                Document entryDoc = new Document();

                if (entry.getKey() instanceof SerializableList) {
                    entryDoc.put("__key", ((SerializableList<?>) entry.getKey()).toDocument());
                } else if (entry.getKey() instanceof SerializableHashMap) {
                    entryDoc.put("__key", ((SerializableHashMap<?, ?>) entry.getKey()).toDocument());
                } else {
                    entryDoc.put("__key", entry.getKey());
                }

                if (entry.getValue() instanceof SerializableList<?> list) {
                    entryDoc.put("__value", list.toDocument());
                } else if (entry.getValue() instanceof SerializableHashMap<?, ?> map) {
                    entryDoc.put("__value", map.toDocument());
                } else {
                    entryDoc.put("__value", entry.getValue());
                }

                doc.put(keyStr, entryDoc);
            }
        }

        return doc;
    }


    @Override
    public MongoSerializable required() {
        needed = true;
        return this;
    }

    @Override
    public String toString() {
        return "SerializableHashMap{" +
                "size=" + internalMap.size() +
                ", elements=" + internalMap +
                '}';
    }
}
