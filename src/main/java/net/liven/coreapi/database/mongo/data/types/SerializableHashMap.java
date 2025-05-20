package net.liven.coreapi.database.mongo.data.types;

import lombok.Getter;
import net.liven.coreapi.database.mongo.data.MongoSerializable;
import org.bson.Document;

import java.io.Serializable;
import java.util.*;
@Getter
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
            List<Document> entries = new ArrayList<>();

            for (Map.Entry<K, V> entry : internalMap.entrySet()) {
                Document entryDoc = new Document();

                // Key handling
                Object keyObj = entry.getKey();
                if (keyObj instanceof SerializableList<?> list) {
                    entryDoc.put("__key", list.toDocument());
                } else if (keyObj instanceof SerializableHashMap<?, ?> map) {
                    entryDoc.put("__key", map.toDocument());
                } else {
                    entryDoc.put("__key", keyObj);
                }

                // Value handling
                Object valueObj = entry.getValue();
                if (valueObj instanceof SerializableList<?> list) {
                    entryDoc.put("__value", list.toDocument());
                } else if (valueObj instanceof SerializableHashMap<?, ?> map) {
                    entryDoc.put("__value", map.toDocument());
                } else {
                    entryDoc.put("__value", valueObj);
                }

                entries.add(entryDoc);
            }

            doc.put("entries", entries);
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

    public Set<K> keys() {
       return internalMap.keySet();
    }
}
