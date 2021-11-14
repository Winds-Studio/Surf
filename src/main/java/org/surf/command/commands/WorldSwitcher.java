package org.surf.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.surf.Main;
import org.surf.command.BaseTabCommand;
import org.surf.util.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorldSwitcher extends BaseTabCommand {

    public WorldSwitcher() {
        super(
                "world",
                "/world <worldName>",
                "l2x9core.command.world",
                "Switch worlds",
                new String[]{
                        "overworld::Teleport your self to the overworld",
                        "nether::Teleport your self to the nether",
                        "end::Teleport your self to the end"
                }
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        Player player = getSenderAsPlayer(sender);
        if (player != null) {
            if (args.length > 0) {
                switch (args[0]) {
                    case "overworld":
                        int x = player.getLocation().getBlockX();
                        int y = player.getLocation().getBlockY();
                        int z = player.getLocation().getBlockZ();
                        World overWorld = Bukkit.getWorld(plugin.getConfig().getString("World-name"));
                        player.teleport(new Location(overWorld, x, y, z));
                        Utils.sendMessage(player, "&6Teleporting to &r&c" + args[0]);
                        break;
                    case "nether":
                        int netherX = player.getLocation().getBlockX();
                        int netherY = player.getLocation().getBlockY();
                        int netherZ = player.getLocation().getBlockZ();
                        World netherWorld = Bukkit.getWorld(plugin.getConfig().getString("World-name").concat("_nether"));
                        if (netherY < 128) {
                            player.teleport(new Location(netherWorld, netherX, 125, netherZ));
                        } else {
                            player.teleport(new Location(netherWorld, netherX, netherY, netherZ));

                        }
                        Utils.sendMessage(player, "&6Teleporting to &r&c" + args[0]);
                        break;
                    case "end":
                        int endX = player.getLocation().getBlockX();
                        int endY = player.getLocation().getBlockY();
                        int endZ = player.getLocation().getBlockZ();
                        World endWorld = Bukkit
                                .getWorld(plugin.getConfig().getString("World-name").concat("_the_end"));
                        player.teleport(new Location(endWorld, endX, endY, endZ));
                        Utils.sendMessage(player, "&6Teleporting to &r&c" + args[0]);
                        break;
                    default:
                        Utils.sendMessage(sender, "&4Error:&r&c Unknown world");
                        break;
                }
            } else {
                sendErrorMessage(sender, "Please include one argument /world <end | overworld | nether>");
            }
        } else {
            sendErrorMessage(sender, PLAYER_ONLY);
        }
    }

    @Override
    public List<String> onTab(String[] args) {
        List<String> list;
        if (args.length > 0) {
            if (args[0].startsWith("o")) {
                list = Collections.singletonList("overworld");
                return list;
            }
            if (args[0].startsWith("e")) {
                list = Collections.singletonList("end");
                return list;
            }
            if (args[0].startsWith("n")) {
                list = Collections.singletonList("nether");
                return list;
            }
        } else {
            list = Arrays.asList("overworld", "nether", "end");
            return list;
        }
        return null;
    }
}
