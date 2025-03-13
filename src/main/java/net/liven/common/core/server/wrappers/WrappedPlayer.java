package net.liven.common.core.server.wrappers;

import java.util.UUID;

public interface WrappedPlayer extends WrappedCommandSender {

    UUID getUniqueId();
    String getName();
    void kickPlayer();
    boolean hasPermission(String permission);

    void closeInventory();

    void performCommand(String s);
}
