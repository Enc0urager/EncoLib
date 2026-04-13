package dev.enco.encolib.scheduler;

public interface WrappedTask<T> {
    void cancel();
    T runnable();
}
