package dev.enco.encolib.utils.logger;

import dev.enco.encolib.utils.version.Version;
import org.bukkit.plugin.java.JavaPlugin;

public class LoggerFactory {
    public ILogger newLogger(JavaPlugin plugin) {
        if (Version.getServerVersion().isHigherThanOrEqualTo(Version.V1_19)) return new ModernLogger(plugin);
        return new LegacyLogger(plugin);
    }
}
