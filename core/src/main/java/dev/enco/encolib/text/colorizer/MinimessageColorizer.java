package dev.enco.encolib.text.colorizer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MinimessageColorizer implements IColorizer {
    private final MiniMessage mini = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacy =  LegacyComponentSerializer.legacySection();

    @Override
    public String colorize(String s) {
        var comp = mini.deserialize(s);
        return legacy.serialize(comp);
    }

    public Component deserialize(String s) {
        return mini.deserialize(s);
    }
}
