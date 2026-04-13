package dev.enco.encolib.scheduler.tasks;

import dev.enco.encolib.scheduler.WrappedTask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public record FoliaTask(ScheduledTask runnable) implements WrappedTask<ScheduledTask> {
    @Override
    public void cancel() {
        runnable.cancel();
    }
}
