package dev.enco.encolib.actions.model;

@FunctionalInterface
public interface ActionExecutor<T> {
    void execute(T target, Object[] args, String[] keys, String[] values);
}
