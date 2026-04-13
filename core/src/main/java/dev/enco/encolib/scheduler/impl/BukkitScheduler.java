package dev.enco.encolib.scheduler.impl;

import dev.enco.encolib.scheduler.IScheduler;
import dev.enco.encolib.scheduler.WrappedTask;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import dev.enco.encolib.scheduler.tasks.BukkitTask;

@RequiredArgsConstructor
public class BukkitScheduler implements IScheduler {
    private final JavaPlugin plugin;

    @Override
    public void run(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    @Override
    public void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    @Override
    public void runLater(Runnable task, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, task, delay);
    }

    @Override
    public void runLaterAsync(Runnable task, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
    }

    @Override
    public WrappedTask<?> runRepeating(Runnable task, long delay, long period) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                task.run();
            }
        };
        runnable.runTaskTimer(plugin, delay, period);
        return new BukkitTask(runnable);
    }

    @Override
    public WrappedTask<?> runRepeatingAsync(Runnable task, long delay, long period) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                task.run();
            }
        };
        runnable.runTaskTimerAsynchronously(plugin, delay, period);
        return new BukkitTask(runnable);
    }
}
