package dev.enco.encolib.scheduler;

public interface IScheduler {
    void run(Runnable task);
    void runAsync(Runnable task);
    void runLater(Runnable task, long delay);
    void runLaterAsync(Runnable task, long delay);
    WrappedTask<?> runRepeating(Runnable task, long delay, long period);
    WrappedTask<?> runRepeatingAsync(Runnable task, long delay, long period);
}
