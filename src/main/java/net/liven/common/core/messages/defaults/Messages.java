package net.liven.common.core.messages.defaults;

import net.liven.common.core.messages.Message;
import net.liven.common.core.messages.MessageObject;

public enum Messages implements Message {
    NO_PERMISSION;

    @Override
    public MessageObject<?> getDefaultMessage() {
        return null;
    }
}
