package dev.enco.encolib.listener;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class ListenerRegistry {
    public <T extends Event> void register(
            JavaPlugin plugin,
            Class<T> clazz,
            EventPriority priority,
            boolean ignoreCancelled,
            EventExecutor executor
    ) {
        Bukkit.getPluginManager().registerEvent(
                clazz,
                new Listener() {},
                priority,
                executor,
                plugin,
                ignoreCancelled
        );
    }
}
