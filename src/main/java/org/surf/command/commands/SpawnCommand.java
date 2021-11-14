package org.surf.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.surf.Main;
import org.surf.command.BaseTabCommand;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand extends BaseTabCommand {
    public SpawnCommand() {
        super(
                "spawn",
                "/spawn <amount> <EntityType>",
                "l2x9core.command.spawnshit"
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        Player player = getSenderAsPlayer(sender);
        if (player != null) {
            if (args.length == 2) {
                try {
                    if (getEntityTypes().contains(args[1])) {
                        int amount = Integer.parseInt(args[0]);
                        for (int i = 0; i < amount; i++) {
                            player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(args[1].toUpperCase()));
                        }
                    } else {
                        sendErrorMessage(sender, "Invalid entity " + args[1]);
                    }
                } catch (NumberFormatException e) {
                    sendErrorMessage(sender, "Invalid argument type the argument " + args[0] + " must be a number");
                }
            } else {
                sendErrorMessage(sender, getUsage());
            }
        } else {
            sendErrorMessage(sender, PLAYER_ONLY);
        }
    }

    public List<String> getEntityTypes() {
        List<String> entityTypes = new ArrayList<>();
        for (EntityType entityType : EntityType.values()) {
            entityTypes.add(entityType.toString().toLowerCase());
        }
        return entityTypes;
    }

    @Override
    public List<String> onTab(String[] args) {
        return getEntityTypes();
    }
}
