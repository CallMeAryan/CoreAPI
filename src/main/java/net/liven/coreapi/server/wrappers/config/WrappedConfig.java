package net.liven.coreapi.server.wrappers.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface WrappedConfig {
    void loadConfig(File file) throws IOException;
    Set<String> getSection();
    void save(File file) throws IOException;
    void set(String path, Object message);
    String getString(String path, String defaultValue);
    boolean has(String path);
    List<String> getStringList(String path);
}
