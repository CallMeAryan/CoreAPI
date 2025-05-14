package net.liven.coreapi.database.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

public class MongoDatabaseManager {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoTemplate mongoTemplate;

    // Manual initialization (call once during app startup)
    public static void initialize(String connectionString, String databaseName) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .uuidRepresentation(UuidRepresentation.STANDARD) // or .JAVA_LEGACY
                .build();

        mongoClient = com.mongodb.client.MongoClients.create(settings);
        database = mongoClient.getDatabase(databaseName);
        mongoTemplate = new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient, databaseName));

    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("MongoDatabaseManager is not initialized.");
        }
        return database;
    }

    public static MongoCollection<Document> getCollection(String name) {
        return getDatabase().getCollection(name);
    }

    public static void saveDocument(String collectionName, Document document) {
        getCollection(collectionName).insertOne(document);
    }

    public static MongoTemplate getMongoTemplate() {
        if (mongoTemplate == null) {
            throw new IllegalStateException("MongoDatabaseManager is not initialized.");
        }
        return mongoTemplate;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
