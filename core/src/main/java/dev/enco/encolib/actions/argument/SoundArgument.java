package dev.enco.encolib.actions.argument;

import org.bukkit.Sound;

public class SoundArgument extends Argument<Sound> {
    public SoundArgument(String name) {
        super(name);
    }

    @Override
    public Sound parse(String input) {
        return Sound.valueOf(input);
    }
}
