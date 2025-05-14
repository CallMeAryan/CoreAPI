package net.liven.coreapi.database.mongo.test;

import net.liven.coreapi.database.mongo.MongoDatabaseManager;
import net.liven.coreapi.database.mongo.data.types.SerializableHashMap;

import java.util.List;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
        MongoDatabaseManager.initialize("mongodb+srv://aryan:testpass001@cluster0.ed1agmf.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0",
                "testdb");
        Profile profile = Profile.loadProfile(Profile.Fields.PLAYER_NAME, "aryan");


        System.out.println(profile.getName());

        SerializableHashMap<Profile.Toggles, UUID> rankList = new SerializableHashMap<>();
        rankList.put(Profile.Toggles.ON, UUID.randomUUID());
        rankList.put(Profile.Toggles.OFF, UUID.randomUUID());


        System.out.println(profile.getRankedPlayers());

        profile.setRankedPlayers(null);

        System.out.println(profile.getRankedPlayers());


      //  System.out.println(profile.getSettings());

      //  profile.toggle(Profile.Toggles.ON);

        // MongoDatabaseManager.saveDocument(profile.getCollectionName(), profile.getDocument());



      //  System.out.println(profile.getSettings());
      //  System.out.println(profile.getName());


    }

}
