package dev.enco.encolib.scheduler.impl;

import dev.enco.encolib.scheduler.IScheduler;
import dev.enco.encolib.scheduler.WrappedTask;
import dev.enco.encolib.scheduler.tasks.FoliaTask;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class FoliaScheduler implements IScheduler {
    private final JavaPlugin plugin;
    private final GlobalRegionScheduler globalScheduler;
    private final AsyncScheduler asyncScheduler;

    public FoliaScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
        globalScheduler = plugin.getServer().getGlobalRegionScheduler();
        asyncScheduler = plugin.getServer().getAsyncScheduler();
    }

    @Override
    public void run(Runnable task) {
        globalScheduler.run(
                plugin,
                t -> task.run()
        );
    }

    @Override
    public void runAsync(Runnable task) {
        asyncScheduler.runNow(
                plugin,
                t -> task.run()
        );
    }

    @Override
    public void runLater(Runnable task, long delay) {
        globalScheduler.runDelayed(
                plugin,
                t -> task.run(), delay
        );
    }

    @Override
    public void runLaterAsync(Runnable task, long delay) {
        asyncScheduler.runDelayed(
                plugin,
                t -> task.run(),
                delay * 50L,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public WrappedTask<?> runRepeating(Runnable task, long delay, long period) {
        return new FoliaTask(
                globalScheduler.runAtFixedRate(
                        plugin,
                        t -> task.run(),
                        delay,
                        period
                )
        );
    }

    @Override
    public WrappedTask<?> runRepeatingAsync(Runnable task, long delay, long period) {
        return new FoliaTask(
                asyncScheduler.runAtFixedRate(
                        plugin,
                        t -> task.run(),
                        delay * 50L,
                        period * 50L,
                        TimeUnit.MILLISECONDS
                )
        );
    }
}
