package net.liven.coreapi.messages.defaults;

import net.liven.coreapi.messages.IMessageFace;
import net.liven.coreapi.messages.MessageObject;

public enum Messages implements IMessageFace {
    NO_PERMISSION, MENU_NOT_FOUND;

    @Override
    public MessageObject<?> getDefaultMessage() {
        return null;
    }
}
