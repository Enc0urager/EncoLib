package dev.enco.encolib.actions.factory;

import dev.enco.encolib.actions.argument.Argument;
import dev.enco.encolib.actions.model.Action;
import dev.enco.encolib.actions.model.ActionMap;
import dev.enco.encolib.actions.model.ParsedAction;
import dev.enco.encolib.actions.registry.ActionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionFactory<T> {
    private final ActionRegistry<T> registry;

    public ActionFactory(ActionRegistry<T> registry) {
        this.registry = registry;
    }

    public ActionMap<T> parse(List<String> lines) {
        List<ParsedAction<T>> compiled = new ArrayList<>();

        for (String line : lines) {
            if (!line.startsWith("[")) continue;

            int end = line.indexOf(']');
            if (end == -1) continue;

            String name = line.substring(1, end);
            String argsRaw = line.substring(end + 1).trim();

            Action<T> def = registry.get(name);
            if (def == null) continue;

            Object[] parsed = new Object[def.getArguments().size()];

            Map<String, String> map = parseArguments(argsRaw);

            for (Argument<?> arg : def.getArguments()) {
                String raw = map.get(arg.name());
                if (raw != null) {
                    parsed[arg.index()] = arg.parse(raw);
                }
            }

            ParsedAction<T> action = (target) -> def.execute(target, parsed);
            compiled.add(action);
        }

        return new ActionMap<>(compiled.toArray(new ParsedAction[0]));
    }

    private Map<String, String> parseArguments(String raw) {
        Map<String, String> map = new HashMap<>();

        if (raw.isEmpty()) return map;

        String[] parts = raw.split(";");

        for (String part : parts) {
            int eq = part.indexOf('=');
            if (eq == -1) continue;

            String key = part.substring(0, eq);
            String value = part.substring(eq + 1);

            map.put(key, value);
        }

        return map;
    }
}
