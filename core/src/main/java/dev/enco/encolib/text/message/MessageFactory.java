package dev.enco.encolib.utils.text.message;

import dev.enco.encolib.utils.version.Version;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageFactory {
    public WrappedMessage newMessage(String message, String[] keys) {
        if (Version.getServerVersion().isHigherThanOrEqualTo(Version.V1_18))
            return ComponentMessage.of(message, keys);

        return TextMessage.of(message, keys);
    }
}
