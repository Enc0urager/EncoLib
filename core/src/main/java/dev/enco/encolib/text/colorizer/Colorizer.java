package dev.enco.encolib.utils.text.colorizer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Colorizer {
    LEGACY(new LegacyColorizer()),
    MINI_MESSAGE(new MinimessageColorizer());

    @Getter
    private final IColorizer colorizer;

    public String colorize(String s) {
        if (s == null || s.isEmpty()) return s;
        return colorizer.colorize(s);
    }
}
