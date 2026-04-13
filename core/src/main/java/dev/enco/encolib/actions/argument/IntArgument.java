package dev.enco.encolib.actions.argument;

public class IntArgument extends Argument<Integer> {
    public IntArgument(String name) {
        super(name);
    }

    @Override
    public Integer parse(String input) {
        return Integer.parseInt(input);
    }
}
