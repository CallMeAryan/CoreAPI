package net.liven.coreapi.database.mongo.data;

public interface MongoKey {
    default String name() {
       return this.toString().toLowerCase();
    }
}
