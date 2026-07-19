package cn.dreeam.surf.modules.checks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemCheck {

    // Config option enabled
    boolean enabled();

    // Enabled for specific item
    boolean appliesTo(ItemStack i);

    boolean canBypass();

    void updateBypassableState(Player player);

    boolean doCheck(ItemStack i);

    SanitizeAction doSanitize(ItemStack i);
}
