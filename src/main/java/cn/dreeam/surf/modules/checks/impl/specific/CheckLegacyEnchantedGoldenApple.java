package cn.dreeam.surf.modules.checks.impl.specific;

import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.modules.checks.ItemCheck;
import cn.dreeam.surf.perm.PermissionNodes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckLegacyEnchantedGoldenApple implements ItemCheck {

    public static final String PERM = PermissionNodes.BYPASS_ITEM_SPECIFIC + "legacyenchantedgoldenapple";

    private boolean canBypass;

    @Override
    public boolean enabled() {
        return Config.ItemChecks.checkRuleRemoveLegacyEnchantedGoldenApple;
    }

    @Override
    public boolean appliesTo(ItemStack i) {
        return i.getType() == Material.GOLDEN_APPLE;
    }

    @Override
    public boolean canBypass() {
        return canBypass;
    }

    @Override
    public void updateBypassableState(Player player) {
        canBypass = player.hasPermission(PERM);
    }

    @Override
    public boolean doCheck(ItemStack i) {
        // Code from https://github.com/xGinko/AnarchyExploitFixes/blob/b82a47bc23462900ece0ec3c30cfce0b25ff36f9/src/main/java/me/moomoo/anarchyexploitfixes/Main.java#L299
        String itemData = i.getData().toString();
        return itemData.equals("GOLDEN_APPLE(0)") || itemData.equals("GOLDEN_APPLE(1)") || itemData.equals("GOLDEN_APPLE0");
    }

    @Override
    public void doSanitize(ItemStack i) {
        // TODO delete potion directly.
        throw new UnsupportedOperationException();
    }
}
