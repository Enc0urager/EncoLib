package dev.enco.encolib.actions.argument;

public class FloatArgument extends Argument<Float> {
    public FloatArgument(String name) {
        super(name);
    }

    @Override
    public Float parse(String input) {
        return Float.parseFloat(input);
    }
}
