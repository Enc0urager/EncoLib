package dev.enco.encolib.text.message;

import org.bukkit.entity.Player;

public interface WrappedMessage {
    void sendRaw(Player player);
    void sendParsed(Player player, String[] values);
}
