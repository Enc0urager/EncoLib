package dev.enco.encolib.utils.text.message;

import dev.enco.encolib.utils.text.formatting.PlaceholderFormatter;
import dev.enco.encolib.utils.text.colorizer.MinimessageColorizer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public record ComponentMessage(
        String raw,
        Component component,
        boolean hasPapi,
        String[] keys
) implements WrappedMessage {
    private static final MinimessageColorizer COLORIZER = new MinimessageColorizer();

    public static ComponentMessage of(String text, String[] keys) {
        return new ComponentMessage(
                text,
                COLORIZER.deserialize(text),
                PlaceholderAPI.containsPlaceholders(text),
                keys
        );
    }

    @Override
    public void sendRaw(Player player) {
        player.sendMessage(component);
    }

    @Override
    public void sendParsed(Player player, String[] values) {
        String msg = raw;
        if (keys != null && keys.length > 0) msg = PlaceholderFormatter.format(msg, keys, values);
        if (hasPapi) msg = PlaceholderAPI.setPlaceholders(player, msg);
        player.sendMessage(COLORIZER.deserialize(msg));
    }
}
