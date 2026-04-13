package dev.enco.encolib.actions.argument;

import org.bukkit.Material;

public class MaterialArgument extends Argument<Material> {
    public MaterialArgument(String name) {
        super(name);
    }

    @Override
    public Material parse(String input) {
        return Material.valueOf(input);
    }
}
