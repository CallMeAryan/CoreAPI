package net.liven.coreAPI.core.messages;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class ConfigManager {
    @Getter
    private final static ConfigManager instance = new ConfigManager();

    private final HashMap<Class<?>, File> messageFiles = new HashMap<>();
    private final HashMap<Class<?>, YamlConfiguration> messageConfigs = new HashMap<>();


    public void addMessageFile(Class<?> clazz, File file) {
        messageFiles.put(clazz, file);
    }

    public YamlConfiguration getMessageConfig(Enum<?> clazz) {
        return messageConfigs.get(clazz.getClass());
    }

    public void addMessageConfig(Class<?> clazz, YamlConfiguration configuration) {
        messageConfigs.put(clazz, configuration);
    }

    public File getMessageFile(Enum<?> anEnum) {
        return messageFiles.get(anEnum.getClass());
    }
}
