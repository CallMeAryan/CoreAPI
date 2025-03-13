package net.liven.coreapi.server.spigot;

import net.liven.coreapi.server.wrappers.config.WrappedConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SpigotConfig implements WrappedConfig {
    private YamlConfiguration config;


    @Override
    public void loadConfig(File file) {
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public Set<String> getSection() {
        return config.getKeys(false);
    }

    @Override
    public void save(File file) throws IOException {
        config.save(file);
    }

    @Override
    public void set(String path, Object message) {
        config.set(path, message);
    }
    @Override
    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }

    @Override
    public boolean has(String path) {
        return config.getConfigurationSection(path) != null;
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }
}
