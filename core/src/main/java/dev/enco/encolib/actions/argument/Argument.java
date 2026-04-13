package dev.enco.encolib.actions.argument;

public abstract class Argument<T> {
    private final String name;
    private int index;

    public Argument(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public int index() {
        return index;
    }

    public void bindIndex(int index) {
        this.index = index;
    }

    public abstract T parse(String input);

    @SuppressWarnings("unchecked")
    public T get(Object[] args) {
        return (T) args[index];
    }
}
