package net.liven.coreapi.messages;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.placeholder.Placeholder;
import net.liven.coreapi.placeholder.PlaceholderManager;
import net.liven.coreapi.server.wrappers.WrappedPlayer;
import net.liven.coreapi.server.wrappers.config.WrappedConfig;
import net.liven.coreapi.utils.ChatUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IMessageFace {
    default String getPath() {
        return this.toString().replace("__", ".").replace("_", "-").toLowerCase();
    }

    MessageObject<?> getDefaultMessage();

    static void generateConfig(Class<?> clazz, File dataFolder) throws IOException {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, clazz.getSimpleName().toLowerCase() + ".yml");
        ConfigManager.getInstance().addMessageFile(clazz, file);

        if (!file.exists()) {
            try {
                file.createNewFile();
                ConfigManager.getInstance().addMessageFile(clazz, file);
            } catch (IOException e) {
                throw new RuntimeException("Could not create config file: " + file.getName(), e);
            }
        }

        ConfigManager.getInstance().addMessageFile(clazz, file);
        WrappedConfig configuration = CoreAPIManager.getInstance().createWrappedConfig();
        configuration.loadConfig(file);
        ConfigManager.getInstance().addMessageConfig(clazz, configuration);

        long configMessageCount = configuration.getSection().size();
        int enumMessageCount = getEnumCount(clazz);

        if (configMessageCount < enumMessageCount) {
            for (Object enumConstant : clazz.getEnumConstants()) {
                if (enumConstant instanceof IMessageFace message) {
                    String path = message.getPath();
                    if (!configuration.has(path)) {
                        if (message.getDefaultMessage().getMessage() instanceof String) {
                            configuration.set(path, message.getDefaultMessage().getMessage());
                        } else if (message.getDefaultMessage().getMessage() instanceof List<?>) {
                            configuration.set(path, message.getDefaultMessage().getMessage());
                        }
                    }
                }
            }
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException("Could not save config file: " + file.getName(), e);
            }
        }
    }

    static int getEnumCount(Class<?> clazz) {
        return clazz.getEnumConstants().length;
    }

    default WrappedConfig getConfiguration() {
        return ConfigManager.getInstance().getMessageConfig((Enum<?>) this);
    }

    default File getFile() {
        return ConfigManager.getInstance().getMessageFile((Enum<?>) this);
    }

    default void sendMessage(WrappedPlayer player, Placeholder... placeholders) {
        MessageObject<?> defaultMessage = getDefaultMessage();
        if (defaultMessage.getMessage() instanceof String) {
            player.sendMessage(getMessage(placeholders));
        } else if (defaultMessage.getMessage() instanceof List<?>) {
            for (String str : getMessageList(placeholders)) {
                player.sendMessage(PlaceholderManager.getInstance().parse(str, player));
            }
        } else {
            throw new RuntimeException("Invalid Message Type");
        }
    }

    default String getMessage(Placeholder... placeholders) {
        String path = getPath();
        String message = getConfiguration().getString(path, "&cInvalid Message, Delete your " + getFile().getName() + " and re-generate");

        for (Placeholder placeholder : placeholders) {
            if (placeholder.value() == null) {
                message = message.replace(placeholder.holder(), "");
            } else {
                message = message.replace(placeholder.holder(), placeholder.value());
            }
        }
        return ChatUtils.translateColors(message);
    }

    default List<String> getMessageList(Placeholder... placeholders) {
        String path = getPath();
        List<String> message = getConfiguration().getStringList(path);
        List<String> parsedList = new ArrayList<>();
        for (String str : message) {
            for (Placeholder placeholder : placeholders) {
                if (placeholder.value() == null) {
                    str = str.replace(placeholder.holder(), "");
                } else {
                    str = str.replace(placeholder.holder(), placeholder.value());
                }
            }
            parsedList.add(str);
        }
        if (parsedList.isEmpty()) {
            parsedList.add("&cInvalid Message, Delete your " + getFile().getName() + " and re-generate");
        }
        return ChatUtils.translateColors(parsedList);
    }
}
