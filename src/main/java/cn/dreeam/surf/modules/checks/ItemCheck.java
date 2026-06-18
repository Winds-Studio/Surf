package cn.dreeam.surf.modules.checks;

import org.bukkit.inventory.ItemStack;

public interface ItemCheck {

    // Config option enabled
    boolean enabled();

    // Enabled for specific item
    boolean appliesTo(ItemStack i);

    boolean canBypass();

    boolean doCheck(ItemStack i);
}
