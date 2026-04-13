package dev.enco.encolib.text.message;

import dev.enco.encolib.text.formatting.PlaceholderFormatter;
import dev.enco.encolib.text.colorizer.Colorizer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public record TextMessage(
        String text,
        boolean hasPapi,
        String[] keys
) implements WrappedMessage {

    public static TextMessage of(String text, String[] keys) {
        return new TextMessage(
                Colorizer.LEGACY.colorize(text),
                PlaceholderAPI.containsPlaceholders(text),
                keys
        );
    }

    @Override
    public void sendRaw(Player player) {
        player.sendMessage(text);
    }

    @Override
    public void sendParsed(Player player, String[] values) {
        String msg = text;
        if (keys != null && keys.length > 0) msg = PlaceholderFormatter.format(msg, keys, values);
        if (hasPapi) msg = PlaceholderAPI.setPlaceholders(player, msg);
        player.sendMessage(Colorizer.LEGACY.colorize(msg));
    }
}
