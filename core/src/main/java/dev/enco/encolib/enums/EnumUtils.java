package dev.enco.encolib.utils.enums;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Consumer;

public class EnumUtils {
    public <T extends Enum<T>> EnumSet<T> toEnumSet(
            Collection<String> values,
            Class<T> enumClass,
            Consumer<String> onError
    ) {
        EnumSet<T> set = EnumSet.noneOf(enumClass);
        for (String value : values) {
            try {
                set.add(Enum.valueOf(enumClass, value));
            } catch (IllegalArgumentException e) {
                onError.accept(value);
            }
        }
        return set;
    }

    public <T extends Enum<T>> T toEnum(
            String value,
            Class<T> enumClass,
            Consumer<String> onError
    ) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            onError.accept(value);
            return null;
        }
    }
}
