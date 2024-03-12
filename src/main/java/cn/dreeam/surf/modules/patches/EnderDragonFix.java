package cn.dreeam.surf.modules.patches;

import cn.dreeam.surf.Surf;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class EnderDragonFix implements Listener {

    // TODO: refresh uuid after dragon die
    private static UUID uuid = null;

    public static void fix(Player p) {
        World world = p.getWorld();

        if (uuid == null) {
            List<Entity> e = world.getEntities();

            e.forEach(d -> {
                if (d instanceof EnderDragon) {
                    EnderDragon dragon = (EnderDragon) d;
                    uuid = dragon.getUniqueId();
                }
            });
        }

        EnderDragon dragon = (EnderDragon) Bukkit.getEntity(uuid);
        Surf.getInstance().adventure().player(Bukkit.getPlayer("Dreeam__")).sendMessage(Component.text(dragon.getHealth()));
        Surf.getInstance().adventure().player(Bukkit.getPlayer("Dreeam__")).sendMessage(Component.text(dragon.getPhase().name()));

        if (dragon.getBossBar() == null) {
            Component component = Component.text().append(Component.text("")).build();
            float progress = 1f; // TODO: need to update with the dragon health
            BossBar.Color color = BossBar.Color.PINK;
            BossBar bossBar = BossBar.bossBar(component, progress, color, BossBar.Overlay.PROGRESS);

            Surf.getInstance().adventure().player(Bukkit.getPlayer("Dreeam__")).sendMessage(Component.text("true"));
            Surf.getInstance().adventure().player(Bukkit.getPlayer("Dreeam__")).showBossBar(bossBar);
        }
    }

    public static void getHealth(Player p) {
        World world = p.getWorld();
        List<Entity> list = world.getEntities();

        list.forEach(e -> {
            if (e instanceof EnderDragon) {
                EnderDragon dragon = (EnderDragon) e;
                double health = dragon.getHealth();
                Surf.getInstance().adventure().player(p).sendMessage(Component.text(health));
            }
        });
    }
}
