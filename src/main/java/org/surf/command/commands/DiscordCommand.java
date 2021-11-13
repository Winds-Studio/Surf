package org.surf.command.commands;

import org.bukkit.command.CommandSender;
import org.surf.Main;
import org.surf.command.BaseCommand;

public class DiscordCommand extends BaseCommand {

    public DiscordCommand() {
        super(
                "discord",
                "/discord",
                "l2x9core.command.discord",
                "Shows a discord link");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        sendMessage(sender, plugin.getConfig().getString("Discord"));
    }
}
