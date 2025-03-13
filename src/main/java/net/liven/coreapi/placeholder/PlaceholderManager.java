package net.liven.coreapi.placeholder;

import lombok.Getter;
import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.server.wrappers.WrappedPlayer;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

@Getter
public class PlaceholderManager {
    @Getter
    private final static PlaceholderManager instance = new PlaceholderManager();
    private final HashMap<String, Placeholder> serverPlaceholders = new HashMap<>();
    private final HashMap<String, Function<UUID, String>> registeredPlaceholders = new HashMap<>();


    public void registerDefaultPlaceholders() {
        registeredPlaceholders.put("uuid", UUID::toString);
        registeredPlaceholders.put("name", (uuid -> {
            WrappedPlayer player = CoreAPIManager.getInstance().getWrappedServer().getPlayer(uuid);
            return player == null ? "" : player.getName();
        }));

        registeredPlaceholders.put("player", (uuid -> {
            WrappedPlayer player = CoreAPIManager.getInstance().getWrappedServer().getPlayer(uuid);
            return player == null ? "" : player.getName();
        }));
    }

    public void updatePlaceholders(Placeholder placeholder) {
        serverPlaceholders.put(placeholder.holder(), placeholder);
    }

    public void registerPlaceholder(String placeholderName, Function<UUID, String> function) {
        registeredPlaceholders.put(placeholderName, function);
    }

    public void resetServerPlaceholders() {
        serverPlaceholders.clear();
    }

    public String parse(String format, WrappedPlayer player, Placeholder... placeholders) {
        String parsed = format;
        for (Placeholder placeholder : placeholders) {
            parsed = parsed.replace(placeholder.holder(), placeholder.getValue());
        }
        for (Placeholder placeholder : serverPlaceholders.values()) {
            parsed = parsed.replace(placeholder.holder(), placeholder.getValue());
        }
        for (String placeholder : registeredPlaceholders.keySet()) {
            String replacement = registeredPlaceholders.get(placeholder).apply(player.getUniqueId());
            if (replacement == null) continue;
            Placeholder registeredPlaceholder = new Placeholder(placeholder, replacement);
            parsed = parsed.replace(registeredPlaceholder.holder(), registeredPlaceholder.getValue());
        }
        return parsed;
    }
}
