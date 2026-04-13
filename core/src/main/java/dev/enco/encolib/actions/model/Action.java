package dev.enco.encolib.actions.model;

import dev.enco.encolib.actions.argument.Argument;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Action<T> {
    private String name;
    private final List<Argument<?>> arguments = new ArrayList<>();
    private ActionExecutor<T> executor;

    public Action<T> name(String name) {
        this.name = name;
        return this;
    }

    public Action<T> argument(Argument<?> arg) {
        arg.bindIndex(arguments.size());
        arguments.add(arg);
        return this;
    }

    public Action<T> arguments(Argument<?>... args) {
        for (Argument<?> arg : args) {
            argument(arg);
        }
        return this;
    }

    public Action<T> executor(ActionExecutor<T> executor) {
        this.executor = executor;
        return this;
    }

    public void execute(T target, Object[] args) {
        executor.execute(target, args);
    }
}