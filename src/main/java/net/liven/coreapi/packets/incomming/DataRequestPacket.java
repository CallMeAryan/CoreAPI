package net.liven.coreapi.packets.incomming;

import java.util.function.Function;

public class DataRequestPacket {
    private final Function<String, String> requestHandler;

    public DataRequestPacket(Function<String, String> requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void prepareResponse(String request) {
        String response = requestHandler.apply(request);

    }

}
