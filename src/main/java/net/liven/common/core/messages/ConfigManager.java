package net.liven.common.core.messages;

import lombok.Getter;
import net.liven.common.core.server.wrappers.config.WrappedConfig;

import java.io.File;
import java.util.HashMap;

public class ConfigManager {
    @Getter
    private final static ConfigManager instance = new ConfigManager();

    private final HashMap<Class<?>, File> messageFiles = new HashMap<>();
    private final HashMap<Class<?>, WrappedConfig> messageConfigs = new HashMap<>();


    public void addMessageFile(Class<?> clazz, File file) {
        messageFiles.put(clazz, file);
    }

    public WrappedConfig getMessageConfig(Enum<?> clazz) {
        return messageConfigs.get(clazz.getClass());
    }

    public void addMessageConfig(Class<?> clazz, WrappedConfig configuration) {
        messageConfigs.put(clazz, configuration);
    }

    public File getMessageFile(Enum<?> anEnum) {
        return messageFiles.get(anEnum.getClass());
    }
}
