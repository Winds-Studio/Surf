package org.surf.command.commands;

import org.bukkit.command.CommandSender;
import org.surf.Main;
import org.surf.util.Utils;

public class BaseCommand extends org.surf.command.BaseCommand {

    public BaseCommand() {
        super(
                "lef",
                "/lef reload | version | help",
                "l2x9core.command.aef",
                "Base command of the plugin",
                new String[]{
                        "reload::Reload the config file",
                        "version::Show the version of the plugin",
                        "help::Shows the help for the plugin"
                }
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        if (args.length > 0) {
            switch (args[0]) {
                case "reload":
                    plugin.reloadConfig();
                    plugin.setupChunkEntityLimit();
                    Utils.sendMessage(sender, Utils.getPrefix() + "&aReloaded configuration file");
                    break;
                case "version":
                    sendMessage(sender, Utils.getPrefix() + "&6Version &r&c" + plugin.getDescription().getVersion());
                    break;
                case "help":
                    sendMessage(sender, Utils.getPrefix() + "&1---&r " + Utils.getPrefix() + "&6Help &r&1---");
                    sendMessage(sender, Utils.getPrefix() + "");
                    plugin.getCommandHandler().getCommands().forEach(command -> {
                        String helpMsg = "&1---&r&3&l /" + command.getName() + "&r&6 Help &r&1---";
                        sendMessage(sender, Utils.getPrefix() + helpMsg);
                        sendMessage(sender, Utils.getPrefix() + "&3Description: " + command.getDescription());
                        if (command.getSubCommands() != null) {
                            if (command.getSubCommands().length > 0) {
                                sendMessage(sender, Utils.getPrefix() + helpMsg.replace("Help", "Subcommands"));
                                for (String subCommand : command.getSubCommands()) {
                                    String[] split = subCommand.split("::");
                                    if (split.length > 0) {
                                        sendMessage(sender, Utils.getPrefix() + "&6 /" + command.getName() + " " + split[0] + " |&r&e " + split[1]);
                                    } else {
                                        sendMessage(sender, Utils.getPrefix() + "&6 /" + command.getName() + " " + subCommand);
                                    }
                                }
                                sendMessage(sender, Utils.getPrefix() + "&1--------------------");
                            }
                        }
                        sendMessage(sender, Utils.getPrefix() + "");
                    });
                    break;
            }
        } else {
            sendErrorMessage(sender, getUsage());
        }
    }
}
