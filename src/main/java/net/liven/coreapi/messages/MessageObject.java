package net.liven.coreapi.messages;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MessageObject<T> {
    private String stringMessage;
    @Getter
    private List<String> listMessage;
    @Getter
    private final MessageType messageType;

    public MessageObject(T message, MessageType messageType) {
        this.messageType = messageType;
        if (message instanceof String) {
            this.stringMessage = (String) message;
        } else if (message instanceof List<?>) {
            listMessage = new ArrayList<>();
            for (Object obj : (List<?>) message) {
                if (obj instanceof String) {
                    this.listMessage.add((String) obj);
                }
            }
        }
    }

    public T getMessage() {
        return (T) this.stringMessage;
    }

}
