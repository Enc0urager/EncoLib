package dev.enco.encolib.actions.argument;

public class StringArgument extends Argument<String> {
    public StringArgument(String name) {
        super(name);
    }

    @Override
    public String parse(String input) {
        return input;
    }
}
