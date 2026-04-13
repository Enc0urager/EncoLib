package dev.enco.encolib.logger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernLogger implements ILogger {
    private final ComponentLogger logger;
    private final LegacyComponentSerializer legacySection;

    public ModernLogger(JavaPlugin plugin) {
        logger = plugin.getComponentLogger();
        this.legacySection = LegacyComponentSerializer.legacySection();
    }

    @Override
    public void info(String s) {
        logger.info(deserialize(s));
    }

    @Override
    public void warn(String s) {
        logger.warn(deserialize(s));
    }

    @Override
    public void error(String s) {
        logger.error(deserialize(s));
    }

    private Component deserialize(String s) {
        return legacySection.deserialize(s);
    }
}
