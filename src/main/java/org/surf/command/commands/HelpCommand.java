package org.surf.command.commands;

import org.bukkit.command.CommandSender;
import org.surf.Main;
import org.surf.command.BaseCommand;
import org.surf.util.Utils;

import java.util.List;

public class HelpCommand extends BaseCommand {
    public HelpCommand() {
        super(
                "help",
                "/help",
                "l2x9core.command.help",
                "Displays a custom help menu");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        List<String> list = plugin.getConfig().getStringList("Help.List");
        String join = String.join("\n", list);
        Utils.sendMessage(sender, join);
    }
}
