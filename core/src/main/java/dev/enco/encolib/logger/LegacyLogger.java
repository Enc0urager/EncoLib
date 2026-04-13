package dev.enco.encolib.logger;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class LegacyLogger implements ILogger {
    private final Logger logger;

    public LegacyLogger(JavaPlugin plugin) {
        this.logger = plugin.getLogger();
    }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void warn(String s) {
        logger.warning(s);
    }

    @Override
    public void error(String s) {
        logger.severe(s);
    }
}
