package dev.enco.encolib.item;

import dev.enco.encolib.compression.CompressionMethod;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@UtilityClass
public class ItemUtils {

    public String encode(ItemStack item, CompressionMethod method) {
        byte[] raw = item.serializeAsBytes();
        byte[] processed = method.compressSafe(raw);
        return Base64.getEncoder().encodeToString(processed);
    }

    public ItemStack decode(String s, CompressionMethod method) {
        byte[] data = Base64.getDecoder().decode(s);
        byte[] decompressed = method.decompressSafe(data);
        return ItemStack.deserializeBytes(decompressed);
    }

    public ItemStack[] findAndRemove(Inventory inventory, Material material) {
        List<ItemStack> result = new ArrayList<>();

        int size = inventory.getSize();

        for (int i = 0; i < size; i++) {
            var stack = inventory.getItem(i);
            if (stack != null && stack.getType() == material) {
                result.add(stack.clone());
                inventory.setItem(i, null);
            }
        }

        return result.toArray(new ItemStack[0]);
    }

    public void giveOrDrop(Player player, ItemStack...stacks) {
        var over = player.getInventory().addItem(stacks);
        if (over.isEmpty()) return;

        World world = player.getWorld();
        Location location = player.getLocation();

        for (var stack : over.values())
            world.dropItemNaturally(location, stack);
    }
}
