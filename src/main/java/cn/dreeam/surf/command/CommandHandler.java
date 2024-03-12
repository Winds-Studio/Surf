package cn.dreeam.surf.command;

import cn.dreeam.surf.Surf;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements TabExecutor {
    private final ArrayList<BaseCommand> commands = new ArrayList<>();
    private final Surf plugin;

    public CommandHandler(Surf plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        addCommand(new cn.dreeam.surf.command.commands.BaseCommand());
    }

    private void addCommand(BaseCommand command) {
        commands.add(command);
        plugin.getCommand(command.getName()).setExecutor(this);
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
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        for (BaseCommand command : commands) {
            if (command.getName().equalsIgnoreCase(cmd.getName())) {
                if (command instanceof BaseTabCommand tabCommand) {
                    return tabCommand.onTab(args);
                } else {
                    List<String> players = new ArrayList<>();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        players.add(player.getName());
                    }
                    return players;
                }
            }
        }
        return Collections.singletonList("Not a tab command");
    }

    public ArrayList<BaseCommand> getCommands() {
        return commands;
    }
}
