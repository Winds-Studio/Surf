package cn.dreeam.surf.modules.checks;

import cn.dreeam.surf.modules.checks.impl.CheckStackAmount;

public class ItemCheckRegistry {

    private static final ItemCheck[] ACTIVE_CHECKS = allChecks();

    // TODO: Only filter checks by config when config reload if we actually have the performance issues.
    /*
    public static void loadChecks() {
        for  (ItemCheck itemCheck : allChecks()) {
            if (itemCheck.enabled()) {
                activeChecks.add(itemCheck);
            }
        }
    }
     */

    public static ItemCheck[] activeChecks() {
        return ACTIVE_CHECKS;
    }

    private static ItemCheck[] allChecks() {
        return new ItemCheck[]{
                new CheckStackAmount()
        };
    }
}
