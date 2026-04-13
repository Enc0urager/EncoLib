package dev.enco.encolib.actions.model;

public interface ParsedExecutor<T> {
    void execute(T target, String[] keys, String[] values);
}
