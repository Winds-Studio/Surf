package cn.dreeam.surf.command;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.command.commands.SurfCommand;
import org.jetbrains.annotations.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter {

    private final List<BaseCommand> commands = new ArrayList<>();
    private final List<String> tabCompletion = new ArrayList<>(List.of(
            "reload",
            "version",
            "help"
    ));
    private final Surf plugin;

    public CommandHandler(Surf plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        addCommand(new SurfCommand());
    }

    private void addCommand(BaseCommand command) {
        commands.add(command);
        plugin.getCommand(command.getName()).setExecutor(this);
        plugin.getCommand(command.getName()).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        for (BaseCommand command : commands) {
            if (command.getName().equalsIgnoreCase(cmd.getName())) {
                if (sender.hasPermission(command.getPermission())) {
                    command.execute(sender, args, plugin);
                } else {
                    command.sendNoPermission(sender);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            // Dreeam - refer to https://github.com/mrgeneralq/sleep-most/blob/5f2f7772c9715cf57530e2af3573652d17cd7420/src/main/java/me/mrgeneralq/sleepmost/commands/SleepmostCommand.java#L135
            for (String completion: tabCompletion) {
                final String arg = args[0];
                if (completion.startsWith(arg) && sender.hasPermission("surf.command." + arg)) {
                    completions.add(completion);
                }
            }

            return completions;
        }

        return null;
    }

    public List<BaseCommand> getCommands() {
        return commands;
    }
}
