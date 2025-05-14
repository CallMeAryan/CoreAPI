package net.liven.coreapi.database.mongo.data;

import org.bson.Document;

/**
 * Interface for Mongo-serializable objects.
 */
public interface MongoSerializable {
    Document toDocument();
    boolean isEmpty();
    MongoSerializable required();
}
