package dev.enco.encolib.actions.registry;

import dev.enco.encolib.actions.model.Action;

import java.util.HashMap;
import java.util.Map;

public class ActionRegistry<T> {
    private final Map<String, Action<T>> actions = new HashMap<>();

    public void register(Action<T> action) {
        actions.put(action.getName(), action);
    }

    public Action<T> get(String name) {
        return actions.get(name);
    }
}
