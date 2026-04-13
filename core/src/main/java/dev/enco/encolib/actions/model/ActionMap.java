package dev.enco.encolib.actions.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionMap<T> {
    private static final String[] EMPTY = new  String[0];
    private final ParsedExecutor<T>[] actions;

    public void execute(T target) {
        execute(target, EMPTY, EMPTY);
    }

    public void execute(T target, String[] keys, String[] values) {
        for (ParsedExecutor<T> action : actions)
            action.execute(target, keys, values);
    }
}
