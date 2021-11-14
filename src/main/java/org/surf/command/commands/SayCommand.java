package org.surf.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.surf.Main;
import org.surf.command.BaseCommand;

public class SayCommand extends BaseCommand {

    public SayCommand() {
        super(
                "say",
                "/say <message>",
                "l2x9core.command.say",
                "Configurable say command");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        if (args.length > 0) {
            String configMessage = plugin.getConfig().getString("SayCommandFormat");
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg.concat(" "));
            }
            Bukkit.getServer().broadcastMessage(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            configMessage.replace("{message}", builder.toString())));
        } else {
            sendErrorMessage(sender, "Message cannot be blank");
        }
    }
}