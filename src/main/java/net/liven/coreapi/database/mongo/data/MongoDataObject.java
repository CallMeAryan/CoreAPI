package net.liven.coreapi.database.mongo.data;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import net.liven.coreapi.database.mongo.MongoDatabaseManager;
import net.liven.coreapi.database.mongo.data.types.*;
import org.bson.Document;

import java.util.*;

@Getter
@Setter
public abstract class MongoDataObject {
    private Document document;
    private final String tableName;

    public MongoDataObject(Document document, String tableName) {
        this.document = document;
        this.tableName = tableName;
    }

    public MongoDataObject(String tableName){
        this.tableName = tableName;
    }

    public static Document load(String collectionName, MongoKey type, Object value) {
        String field = type.name().toLowerCase();
        MongoCollection<Document> collection = MongoDatabaseManager.getCollection(collectionName);

        Document query = new Document(field, value);

        Document found = collection.find(query).first();

        if (found == null) {
            throw new IllegalStateException("Document not found for " + field + " = " + value + " in table " + collectionName);
        }

        return found;
    }


    protected MongoDataObject createNew() {
        this.document = new Document();
        return this;
    }




    protected void save() {
        if (document == null) {
            throw new IllegalStateException("No document to save.");
        }
        MongoCollection<Document> collection = MongoDatabaseManager.getCollection(tableName);
        if (document.containsKey("_id")) {
            collection.replaceOne(new Document("_id", document.get("_id")), document);
        } else {
            collection.insertOne(document);
        }
    }

    protected String getString(MongoKey key) {
        return getOrDefaultPrimitive(key, String.class);
    }

    protected Integer getInt(MongoKey key) {
        return getOrDefaultPrimitive(key, Integer.class);
    }

    protected Long getLong(MongoKey key) {
        return getOrDefaultPrimitive(key, Long.class);
    }

    protected Boolean getBoolean(MongoKey key) {
        return document.getBoolean(key.name().toLowerCase(), Boolean.FALSE);
    }

    protected UUID getUUID(MongoKey key) {
        return document.get(key.name().toLowerCase(), UUID.class);
    }

    protected Date getDate(MongoKey key) {
        return document.getDate(key.name().toLowerCase());
    }

    private <T> T getOrDefaultPrimitive(MongoKey key, Class<T> type) {
        String field = key.name().toLowerCase();
        if (document == null) {
            throw new IllegalStateException("Document is null, cannot get field " + field);
        }
        return document.get(field, type);
    }


//    @SuppressWarnings("unchecked")
//    public <K, V> SerializableHashMap<K, V> getStaticMap(MongoKey mongoKey, Class<K> keyClass, Class<V> valueClass) {
//        SerializableHashMap<K, V> map = new SerializableHashMap<>();
//        Document doc = (Document) document.get(mongoKey.name());
//
//        if (doc == null) {
//            document.put(mongoKey.name(), map);
//            return map;
//        }
//
//        List<?> keys = (List<?>) doc.get("__key");
//        List<?> values = (List<?>) doc.get("__value");
//
//        if (keys == null || values == null || keys.size() != values.size()) return map;
//        for (int i = 0; i < keys.size(); i++) {
//            Object keyRaw = keys.get(i);
//            Object valueRaw = values.get(i);
//            K key = null;
//            V value = null;
//
//            if(keyClass.isEnum()){
//                for(Object k : keyRaw.getClass().getEnumConstants()) {
//                    if(k.toString().equals(keyRaw.toString())){
//                        key = (K) k;
//                        break;
//                    }
//                }
//            }
//
//            if (valueClass.isEnum()) {
//                for(Object v : valueRaw.getClass().getEnumConstants()) {
//                    if(v.toString().equals(valueRaw.toString())){
//                        value = (V) v;
//                        break;
//                    }
//                }
//            }
//            if(key == null) {
//                key = (K) keyRaw;
//            }
//            if(value == null) {
//                value = (V) valueRaw;
//            }
//
//            map.put(key, value);
//        }
//
//        return map;
//    }

    @SuppressWarnings("unchecked")
    public <K, V> SerializableHashMap<K, V> getStaticMap(MongoKey mongoKey, Class<K> keyClass, Class<V> valueClass) {
        SerializableHashMap<K, V> map = new SerializableHashMap<>();
        Document doc = (Document) document.get(mongoKey.name().toLowerCase());

        if (doc == null) {
            document.put(mongoKey.name().toLowerCase(), map);
            return map;
        }

        for (Map.Entry<String, Object> entry : doc.entrySet()) {
            String keyStr = entry.getKey();
            if (keyStr.startsWith("__")) continue;

            Document entryDoc = (Document) entry.getValue();
            Object rawKey = entryDoc.get("__key");
            Object rawValue = entryDoc.get("__value");

            K key = parseEnumOrBasic(keyClass, rawKey);
            V value = parseEnumOrBasic(valueClass, rawValue);

            map.put(key, value);
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    private <T> T parseEnumOrBasic(Class<T> clazz, Object raw) {
        if (clazz.isEnum() && raw instanceof String s) {
            for (T constant : clazz.getEnumConstants()) {
                if (constant.toString().equals(s)) {
                    return constant;
                }
            }
        }
        return (T) raw;
    }


    @SuppressWarnings("unchecked")
    protected <T> SerializableList<T> getSerializableList(MongoKey key) {
        String field = key.name().toLowerCase();
        Object raw = document.get(field);

        if (raw instanceof Document docValue) {
            SerializableList<T> list = (SerializableList<T>) SerializableList.fromDocument(docValue);
            if (list != null) {
                return list;
            }
        }
        return new SerializableList<>();
    }

    protected void setField(MongoKey key, Object value) {
        String field = key.name().toLowerCase();

        Object dbValue = value;
        if (value instanceof MongoSerializable ms) {
            dbValue = ms.toDocument();
        }

        // Update in memory
        document.put(field, dbValue);

        // Update in MongoDB
        MongoCollection<Document> collection = MongoDatabaseManager.getCollection(getTableName());
        Document query = new Document("_id", document.get("_id")); // assuming _id is present
        Document update = new Document("$set", new Document(field, dbValue));

        collection.updateOne(query, update);
    }

}
