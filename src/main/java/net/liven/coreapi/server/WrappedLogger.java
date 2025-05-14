package net.liven.coreapi.server;

public interface WrappedLogger {
    void warn(String message);
    void info(String message);
    void error(String message);
    void debug(String message);
}
