package net.liven.coreapi.database.mongo.data;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import net.liven.coreapi.database.mongo.MongoDatabaseManager;
import net.liven.coreapi.database.mongo.data.types.*;
import net.liven.coreapi.database.mongo.test.Profile;
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

        return collection.find(query).first();
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

    @SuppressWarnings("unchecked")
    public <K, V> SerializableHashMap<SerializableList<K>, SerializableList<V>> getDoubleListMap(MongoKey mongoKey, Class<K> keyListType, Class<V> valueListType) {
        SerializableHashMap<SerializableList<K>, SerializableList<V>> map = new SerializableHashMap<>();
        Document doc = (Document) document.get(mongoKey.name().toLowerCase());

        if (doc == null) {
            document.put(mongoKey.name().toLowerCase(), map.toDocument());
            return map;
        }


        List<Document> entries = (List<Document>) doc.get("entries");
        if (entries == null) return map;

        for (Document entryDoc : entries) {
            Document keyDoc = (Document) entryDoc.get("__key");
            Document valueDoc = (Document) entryDoc.get("__value");

            SerializableList<?> rawKeyList = SerializableList.fromDocument(keyDoc);
            SerializableList<?> rawValueList = SerializableList.fromDocument(valueDoc);

            SerializableList<K> keyList = keyListType.isEnum()
                    ? SerializableList.convertEnumList((SerializableList<String>) rawKeyList, (Class<? extends Enum>) keyListType)
                    : (SerializableList<K>) rawKeyList;

            SerializableList<V> valueList = valueListType.isEnum()
                    ? SerializableList.convertEnumList((SerializableList<String>) rawValueList, (Class<? extends Enum>) valueListType)
                    : (SerializableList<V>) rawValueList;

            map.put(keyList, valueList);
        }

        return map;
    }


    @SuppressWarnings("unchecked")
    public <K, V> SerializableHashMap<K, SerializableList<V>> getValueListMap(MongoKey mongoKey, Class<K> keyType, Class<V> valueListType) {
        SerializableHashMap<K, SerializableList<V>> map = new SerializableHashMap<>();
        Document doc = (Document) document.get(mongoKey.name().toLowerCase());


        if (doc == null) {
            document.put(mongoKey.name().toLowerCase(), map.toDocument());
            return map;
        }
        List<Document> entries = (List<Document>) doc.get("entries");
        System.out.println(entries);
        if (entries == null) return map;

        for (Document entryDoc : entries) {
            Object rawKey = entryDoc.get("__key");
            Document valueDoc = (Document) entryDoc.get("__value");

            SerializableList<?> rawValueList = SerializableList.fromDocument(valueDoc);
            SerializableList<V> valueList = valueListType.isEnum()
                    ? SerializableList.convertEnumList((SerializableList<String>) rawValueList, (Class<? extends Enum>) valueListType)
                    : (SerializableList<V>) rawValueList;

            K key = parseEnumOrBasic(keyType, rawKey);
            map.put(key, valueList);
        }

        return map;
    }


    @SuppressWarnings("unchecked")
    public <K, V> SerializableHashMap<SerializableList<K>, V> getKeyListMap(MongoKey mongoKey, Class<K> keyListType, Class<V> valueType) {
        SerializableHashMap<SerializableList<K>, V> map = new SerializableHashMap<>();
        Document doc = (Document) document.get(mongoKey.name().toLowerCase());

        if (doc == null) {
            document.put(mongoKey.name().toLowerCase(), map.toDocument());
            return map;
        }


        List<Document> entries = (List<Document>) doc.get("entries");
        if (entries == null) return map;

        for (Document entryDoc : entries) {
            Document keyDoc = (Document) entryDoc.get("__key");
            Object rawValue = entryDoc.get("__value");

            SerializableList<?> rawKeyList = SerializableList.fromDocument(keyDoc);
            SerializableList<K> keyList = keyListType.isEnum()
                    ? SerializableList.convertEnumList((SerializableList<String>) rawKeyList, (Class<? extends Enum>) keyListType)
                    : (SerializableList<K>) rawKeyList;

            V value = parseEnumOrBasic(valueType, rawValue);
            map.put(keyList, value);
        }

        return map;
    }




    @SuppressWarnings("unchecked")
    public <K, V> SerializableHashMap<K, V> getStaticMap(MongoKey mongoKey, Class<K> keyClass, Class<V> valueClass) {
        SerializableHashMap<K, V> map = new SerializableHashMap<>();
        Document doc = (Document) document.get(mongoKey.name().toLowerCase());

        if (doc == null) {
            document.put(mongoKey.name().toLowerCase(), map.toDocument());
            return map;
        }

        List<Document> entries = (List<Document>) doc.get("entries");
        if (entries == null) return map;

        for (Document entryDoc : entries) {
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
