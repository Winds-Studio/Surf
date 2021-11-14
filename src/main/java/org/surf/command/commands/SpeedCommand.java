package org.surf.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.surf.Main;
import org.surf.command.BaseCommand;

public class SpeedCommand extends BaseCommand {

    public SpeedCommand() {
        super(
                "speed",
                "/speed <number>",
                "l2x9core.command.speed",
                "Turn up your fly speed");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        Player player = getSenderAsPlayer(sender);
        if (player != null) {
            try {
                if (args.length > 0) {
                    float speed = Float.parseFloat(args[0]);
                    if (!(speed > 1)) {
                        player.setFlySpeed(speed);
                        sendMessage(player, "&6Fly speed set to&r&c " + speed);
                    } else {
                        sendErrorMessage(player, "Flying speed must not be above 1");
                    }
                } else {
                    sendMessage(sender, "&6Please note that the default flight speed is&r&c 0.1");
                    sendErrorMessage(sender, getUsage());

                }
            } catch (NumberFormatException e) {
                sendErrorMessage(player, getUsage());
            }
        } else {
            sendErrorMessage(sender, PLAYER_ONLY);
        }
    }
}
