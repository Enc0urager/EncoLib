package dev.enco.encolib.actions.argument;

public class DoubleArgument extends Argument<Double> {
    public DoubleArgument(String name) {
        super(name);
    }

    @Override
    public Double parse(String input) {
        return Double.parseDouble(input);
    }
}
