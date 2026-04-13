package dev.enco.encolib.actions.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionMap<T> {
    private final ParsedAction<T>[] actions;

    public void execute(T target) {
        for (ParsedAction<T> action : actions)
            action.execute(target);
    }
}
