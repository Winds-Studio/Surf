package org.surf.command.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.surf.Main;
import org.surf.command.BaseCommand;

public class OpenInv extends BaseCommand {
    public OpenInv() {
        super(
                "open",
                "/open <inv | ender> <player>",
                "l2x9core.command.openinv",
                "Open peoples inventories",
                new String[]{
                        "inventory::Open the inventory of the specified player",
                        "ender::Open the ender chest of the specified player"
                }
                );
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        Player player = getSenderAsPlayer(sender);
        if (player != null) {
            if (args.length < 2) {
                sendErrorMessage(sender, getUsage());
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (target.isOnline()) {
                    switch (args[0]) {
                        case "ender":
                            player.openInventory(target.getEnderChest());
                            break;
                        case "inv":
                        case "inventory":
                            player.openInventory(target.getInventory());
                            break;
                        default:
                            sendErrorMessage(sender, "Unknown argument " + args[0]);
                    }
                } else {
                    sendErrorMessage(sender, "Player " + args[0] + " not online");
                }
            }
        } else {
            sendErrorMessage(sender, PLAYER_ONLY);
        }
    }
}
