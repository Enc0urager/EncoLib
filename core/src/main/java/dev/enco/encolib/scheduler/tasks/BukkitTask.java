package dev.enco.encolib.scheduler.tasks;

import dev.enco.encolib.scheduler.WrappedTask;
import org.bukkit.scheduler.BukkitRunnable;

public record BukkitTask(BukkitRunnable runnable) implements WrappedTask<BukkitRunnable> {
    @Override
    public void cancel() {
        runnable.cancel();
    }
}
