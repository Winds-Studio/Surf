package cn.dreeam.surf.command.commands;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Util;
import org.bukkit.command.CommandSender;

public class SurfCommand extends cn.dreeam.surf.command.BaseCommand {

    public SurfCommand() {
        super(
                "surf",
                "/surf reload | version | help",
                "surf.command.main",
                "Base command of the plugin",
                new String[]{
                        "reload::Reload the config file",
                        "version::Show the version of the plugin",
                        "help::Shows the help for the plugin"
                }
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args, Surf plugin) {
        if (args.length > 0) {
            switch (args[0]) {
                case "reload":
                    Surf.getInstance().loadConfig();
                    Util.sendMessage(sender, Util.getPrefix() + "&aReloaded configuration file");
                    break;
                case "version":
                    Util.sendMessage(sender, Util.getPrefix() + "&6Version &r&c" + plugin.getDescription().getVersion());
                    break;
                case "help":
                    Util.sendMessage(sender, Util.getPrefix() + "&1---&r " + Util.getPrefix() + "&6Help &r&1---");
                    Util.sendMessage(sender, Util.getPrefix());
                    plugin.getCommandHandler().getCommands().forEach(command -> {
                        String helpMsg = "&1---&r&3&l /" + command.getName() + "&r&6 Help &r&1---";
                        Util.sendMessage(sender, Util.getPrefix() + helpMsg);
                        Util.sendMessage(sender, Util.getPrefix() + "&3Description: " + command.getDescription());
                        if (command.getSubCommands() != null) {
                            if (command.getSubCommands().length > 0) {
                                Util.sendMessage(sender, Util.getPrefix() + helpMsg.replace("Help", "Subcommands"));
                                for (String subCommand : command.getSubCommands()) {
                                    String[] split = subCommand.split("::");
                                    if (split.length > 0) {
                                        Util.sendMessage(sender, Util.getPrefix() + "&6 /" + command.getName() + " " + split[0] + " |&r&e " + split[1]);
                                    } else {
                                        Util.sendMessage(sender, Util.getPrefix() + "&6 /" + command.getName() + " " + subCommand);
                                    }
                                }
                                Util.sendMessage(sender, Util.getPrefix() + "&1--------------------");
                            }
                        }
                    });
                    break;
            }
        } else {
            sendErrorMessage(sender, getUsage());
        }
    }
}
