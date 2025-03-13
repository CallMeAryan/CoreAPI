package net.liven.common.core.server.wrappers.config;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface WrappedConfig {
    void loadConfig(File file);
    Set<String> getSection();
    void save(File file) throws IOException;
    void set(String path, Object message);
    String getString(String path, String defaultValue);
    boolean has(String path);
}
