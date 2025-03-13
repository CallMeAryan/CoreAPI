package net.liven.common.core.messages;

import java.util.ArrayList;
import java.util.List;

public class MessageObject<T> {
    private final String StringMessage;
    private final List<String> ListMessage = new ArrayList<>();

    public MessageObject(T message) {
        if (message instanceof String) {
            this.StringMessage = (String) message;
        } else if (message instanceof List<?>) {
            this.StringMessage = "";
            for (Object obj : (List<?>) message) {
                if (obj instanceof String) {
                    this.ListMessage.add((String) obj);
                }
            }
        } else {
            this.StringMessage = "";
        }
    }

    public T getMessage() {
        return (T) this.StringMessage;
    }

}
