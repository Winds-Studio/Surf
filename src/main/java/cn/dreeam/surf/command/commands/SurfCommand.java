package cn.dreeam.surf.command.commands;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.modules.patches.EnderDragonFix;
import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    plugin.reloadConfig();
                    ConfigCache.loadConfig();
                    sendMessage(sender, Utils.getPrefix() + "&aReloaded configuration file");
                    break;
                case "version":
                    sendMessage(sender, Utils.getPrefix() + "&6Version &r&c" + plugin.getDescription().getVersion());
                    break;
                case "help":
                    sendMessage(sender, Utils.getPrefix() + "&1---&r " + Utils.getPrefix() + "&6Help &r&1---");
                    sendMessage(sender, Utils.getPrefix());
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
                    });
                    break;
                case "fixdargon":
                    if (sender instanceof Player p) {
                        EnderDragonFix.fix(p);
                    } else {
                        sendErrorMessage(sender, "You should only use this command in game!");
                    }
                    break;
                case "checkdargon":
                    if (sender instanceof Player p) {
                        EnderDragonFix.getHealth(p);
                    } else {
                        sendErrorMessage(sender, "You should only use this command in game!");
                    }
                    break;
            }
        } else {
            sendErrorMessage(sender, getUsage());
        }
    }
}
