package net.liven.coreapi.database.mongo.test;

import net.liven.coreapi.database.mongo.MongoDatabaseManager;
import net.liven.coreapi.database.mongo.data.types.SerializableHashMap;
import net.liven.coreapi.database.mongo.data.types.SerializableList;

import java.util.List;
import java.util.UUID;

public class test {
    public static void main(String[] args) {
            MongoDatabaseManager.initialize("mongodb+srv://aryan:testpass001@cluster0.ed1agmf.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0",
                    "testdb");
        Profile profile = Profile.loadProfile(Profile.Fields.PLAYER_NAME, "aryaasdn");
      //  profile.runDataTypeTest();


        if (profile == null) {
            System.out.println("profile is null");
            return;
        }

        SerializableHashMap<SerializableList<String>, String> keyMap = profile.getKeyListMap();
        SerializableHashMap<SerializableList<String>, SerializableList<String>> doubleList = profile.getDoubleListMap();
        SerializableHashMap<String, SerializableList<String>> valueMap = profile.getValueListMap();
        System.out.println(valueMap);

        for (String s : valueMap.keys()){
            SerializableList<String> list = valueMap.get(s);
            System.out.println(s);
            for (String s2 : list){
                System.out.println(s2);
            }
        }


    }

}
