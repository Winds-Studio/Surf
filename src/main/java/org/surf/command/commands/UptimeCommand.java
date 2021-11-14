package org.surf.command.commands;


import org.bukkit.command.CommandSender;
import org.surf.Main;
import org.surf.command.BaseCommand;
import org.surf.util.Utils;

public class UptimeCommand extends BaseCommand {

    public UptimeCommand() {
        super(
                "uptime",
                "/uptime",
                "l2x9core.command.uptime",
                "Show the uptime of the server");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        sendMessage(sender, "&6The server has had &r&c" + Utils.getFormattedInterval(System.currentTimeMillis() - Main.startTime) + "&r&6 uptime");
    }
}
