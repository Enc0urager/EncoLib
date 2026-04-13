package dev.enco.encolib.text.message;

import lombok.RequiredArgsConstructor;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public enum MessageType {
    COMPONENT(ComponentMessage::of),
    TEXT(TextMessage::of);

    private final BiFunction<String, String[], ? extends WrappedMessage> function;

    public WrappedMessage newMessage(String text, String[] keys) {
        return function.apply(text, keys);
    }
}
