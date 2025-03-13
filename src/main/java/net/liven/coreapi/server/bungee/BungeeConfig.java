package net.liven.coreapi.server.bungee;

import net.liven.coreapi.server.wrappers.config.WrappedConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BungeeConfig implements WrappedConfig {
    private Configuration config;

    @Override
    public void loadConfig(File file) throws IOException {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    @Override
    public Set<String> getSection() {
        return new HashSet<>(config.getKeys());
    }

    @Override
    public void save(File file) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
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
        return config.getSection(path) != null;
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }
}
