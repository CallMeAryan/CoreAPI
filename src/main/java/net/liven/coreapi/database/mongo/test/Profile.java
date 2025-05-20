package net.liven.coreapi.database.mongo.test;

import lombok.Getter;
import net.liven.coreapi.database.mongo.data.MongoDataObject;
import net.liven.coreapi.database.mongo.data.MongoKey;
import net.liven.coreapi.database.mongo.data.types.SerializableHashMap;
import net.liven.coreapi.database.mongo.data.types.SerializableList;
import org.bson.Document;

import java.util.Date;
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
        Document profileDoc  = MongoDataObject.load(collectionName, key, value);
        if(profileDoc == null) {
            return null;
        }
        return new Profile(profileDoc);
    }

    public static Profile createNewProfile(String name, UUID id){
        Profile profile = new Profile();
        profile.createNew();
        profile.setField(Fields.PLAYER_NAME, name);
        profile.setField(Fields.PLAYER_ID, id);
        profile.setField(Fields.CREATED_DATE, new Date());
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
        CREATED_DATE,
        MAP_STATIC,
        MAP_KEY_LIST,
        MAP_VALUE_LIST,
        MAP_DOUBLE_LIST;
    }

    public void toggle(Toggles toggle){
        SerializableList<Toggles> toggles = super.getSerializableList(Fields.PLAYER_SETTINGS);
        toggles.add(toggle);
        super.setField(Fields.PLAYER_SETTINGS, toggles);
    }

    public SerializableList<Toggles> getSettings(){
        return SerializableList.convertEnumList(super.getSerializableList(Fields.PLAYER_SETTINGS), Toggles.class);
    }

    public int getAge(){
        return super.getInt(Fields.PLAYER_AGE);
    }

    public String getName(){
        return super.getString(Fields.PLAYER_NAME);
    }

    public UUID getId(){
        return super.getUUID(Fields.PLAYER_ID);
    }

    public Date getCreatedDate() {
        return super.getDate(Fields.CREATED_DATE);
    }

    // --- MAP METHODS ---

    public void setStaticMap(SerializableHashMap<String, Integer> map) {
        super.setField(Fields.MAP_STATIC, map);
    }

    public SerializableHashMap<String, Integer> getStaticMap() {
        return super.getStaticMap(Fields.MAP_STATIC, String.class, Integer.class);
    }

    public void setKeyListMap(SerializableHashMap<SerializableList<String>, String> map) {
        super.setField(Fields.MAP_KEY_LIST, map);
    }

    public SerializableHashMap<SerializableList<String>, String> getKeyListMap() {
        return super.getKeyListMap(Fields.MAP_KEY_LIST, String.class, String.class);
    }

    public void setValueListMap(SerializableHashMap<String, SerializableList<String>> map) {
        super.setField(Fields.MAP_VALUE_LIST, map);
    }

    public SerializableHashMap<String, SerializableList<String>> getValueListMap() {
        return super.getValueListMap(Fields.MAP_VALUE_LIST, String.class, String.class);
    }

    public void setDoubleListMap(SerializableHashMap<SerializableList<String>, SerializableList<String>> map) {
        super.setField(Fields.MAP_DOUBLE_LIST, map);
    }

    public SerializableHashMap<SerializableList<String>, SerializableList<String>> getDoubleListMap() {
        return super.getDoubleListMap(Fields.MAP_DOUBLE_LIST, String.class, String.class);
    }

    public void runDataTypeTest() {

        this.setField(Fields.PLAYER_ID, UUID.randomUUID());
        this.setField(Fields.PLAYER_AGE, 25);
        this.setField(Fields.CREATED_DATE, new Date());

        SerializableList<UUID> friends = new SerializableList<>();
        friends.add(UUID.randomUUID());
        this.setField(Fields.PLAYER_FRIENDS, friends);

        SerializableList<Toggles> toggles = new SerializableList<>();
        toggles.add(Toggles.ON);
        this.setField(Fields.PLAYER_SETTINGS, toggles);

        SerializableHashMap<String, Integer> staticMap = new SerializableHashMap<>();
        staticMap.put("points", 100);
        this.setStaticMap(staticMap);

        SerializableHashMap<SerializableList<String>, String> keyListMap = new SerializableHashMap<>();
        SerializableList<String> keyList = new SerializableList<>();
        keyList.add("match1");
        keyListMap.put(keyList, "playerA");
        this.setKeyListMap(keyListMap);

        SerializableHashMap<String, SerializableList<String>> valueListMap = new SerializableHashMap<>();
        SerializableList<String> valueList = new SerializableList<>();
        valueList.add("playerB");
        valueListMap.put("match2", valueList);
        this.setValueListMap(valueListMap);

        SerializableHashMap<SerializableList<String>, SerializableList<String>> doubleListMap = new SerializableHashMap<>();
        SerializableList<String> keyList2 = new SerializableList<>();
        keyList2.add("match3");
        SerializableList<String> valueList2 = new SerializableList<>();
        valueList2.add("playerC");
        doubleListMap.put(keyList2, valueList2);
        this.setDoubleListMap(doubleListMap);

        this.save();
    }
}
