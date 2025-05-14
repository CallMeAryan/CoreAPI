package net.liven.coreapi.database.mongo.test;

import lombok.Getter;
import net.liven.coreapi.database.mongo.data.MongoDataObject;
import net.liven.coreapi.database.mongo.data.MongoKey;
import net.liven.coreapi.database.mongo.data.types.SerializableHashMap;
import net.liven.coreapi.database.mongo.data.types.SerializableList;
import org.bson.Document;

import java.util.UUID;

public class Profile extends MongoDataObject {
    @Getter
    private final static String collectionName = "player_profiles";

    public Profile() {
        super(collectionName);
    }

    public Profile(Document document) {
        super(document, collectionName);
    }

    public static Profile loadProfile(MongoKey key, Object value) {
        return new Profile(MongoDataObject.load(collectionName, key, value));
    }

    public static Profile createNewProfile(String name, UUID id){
        Profile profile = new Profile();
        profile.createNew();
        profile.setField(Fields.PLAYER_NAME, name);
        profile.setField(Fields.PLAYER_ID, id);
        return profile;
    }


    public void addFriend(UUID id) {
        SerializableList<UUID> friends = super.getSerializableList(Fields.PLAYER_FRIENDS);
        friends.add(id);
        super.setField(Fields.PLAYER_FRIENDS, friends);
    }

    public enum Toggles{
        ON,
        OFF
    }

    public enum Fields implements MongoKey {
        PLAYER_NAME,
        PLAYER_ID,
        PLAYER_AGE,
        PLAYER_FRIENDS,
        PLAYER_SETTINGS,
        RANK_PLAYERS;
    }

    public SerializableHashMap<Profile.Toggles, UUID> getRankedPlayers() {
       return super.getStaticMap(Fields.RANK_PLAYERS, Toggles.class, UUID.class);
    }

    public void setRankedPlayers(SerializableHashMap<Profile.Toggles, UUID> rankedPlayers) {
        super.setField(Fields.RANK_PLAYERS, rankedPlayers);
    }

    public int getAge(){
        return super.getInt(Fields.PLAYER_AGE);
    }

    public String getName(){
       return super.getString(Fields.PLAYER_NAME);
    }

    public void toggle(Toggles toggle){
        SerializableList<Toggles> toggles = super.getSerializableList(Fields.PLAYER_SETTINGS);
        toggles.add(toggle);
        super.setField(Fields.PLAYER_SETTINGS, toggles);
    }

    public SerializableList<Toggles> getSettings(){
        return SerializableList.convertEnumList(super.getSerializableList(Fields.PLAYER_SETTINGS), Toggles.class);
    }

    public UUID getId(){
        return super.getUUID(Fields.PLAYER_ID);
    }

}
