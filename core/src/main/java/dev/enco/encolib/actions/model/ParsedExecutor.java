package dev.enco.encolib.actions.model;

public interface ParsedAction<T> {
    void execute(T target);
}
