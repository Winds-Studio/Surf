package cn.dreeam.surf.modules.misc.patch;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.PlatformUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NBTBan implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Config.Patch.preventNBTBanEnabled) return;

        Inventory inv = event.getPlayer().getInventory();

        if (PlatformUtil.isNewerAndEqual(20, 5)) {
            for (ItemStack i : inv) {
                if (i != null && i.getType().name().contains("SHULKER_BOX")) {
                    if (!i.hasItemMeta()) continue;

                    int itemSize = i.getItemMeta().getAsComponentString().length();

                    if (itemSize > Config.Patch.preventNBTBanLimit) {
                        inv.remove(i);
                        MessageUtil.sendMessage(event.getPlayer(), Config.Patch.preventNBTBanMessage);
                    }
                }
            }
        } else {
            for (ItemStack i : inv) {
                if (i != null && i.getType().name().contains("SHULKER_BOX")) {
                    if (!i.hasItemMeta()) continue;

                    int itemSize = i.getItemMeta().getAsString().length();

                    if (itemSize > Config.Patch.preventNBTBanLimit) {
                        inv.remove(i);
                        MessageUtil.sendMessage(event.getPlayer(), Config.Patch.preventNBTBanMessage);
                    }
                }
            }
        }
    }
}
