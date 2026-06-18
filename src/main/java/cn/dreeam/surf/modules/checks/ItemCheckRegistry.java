package cn.dreeam.surf.modules.checks;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemCheckRegistry {

    private static final ItemCheck[] ACTIVE_ITEM_CHECKS = new ItemCheck[]{};

    public static void loadChecks() {

    }

    public static ItemCheck[] activeChecks() {
        return ACTIVE_ITEM_CHECKS;
    }
}
