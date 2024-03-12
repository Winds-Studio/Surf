package cn.dreeam.surf.command;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand {

    public final String CONSOLE_ONLY = "This command is console only";
    public final String PLAYER_ONLY = "This command is player only";
    private final String name;
    private final String usage;
    private final String permission;
    private String description;
    private String[] subCommands;

    public BaseCommand(String name, String usage, String permission) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
    }

    public BaseCommand(String name, String usage, String permission, String description) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
        this.description = description;
    }

    public BaseCommand(String name, String usage, String permission, String description, String[] subCommands) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
        this.description = description;
        this.subCommands = subCommands;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public String[] getSubCommands() {
        return subCommands;
    }

    public void sendMessage(CommandSender sender, String message) {
        Utils.sendMessage(sender, message);
    }

    public void sendNoPermission(CommandSender sender) {
        Utils.sendMessage(sender, "&4Error:&r&c You are lacking the permission " + getPermission());
    }

    public void sendErrorMessage(CommandSender sender, String message) {
        String finalMessage = "&4Error:&r&c " + message;
        Utils.sendMessage(sender, finalMessage);
    }

    public Player getSenderAsPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player) sender;
        } else {
            return null;
        }
    }

    public abstract void execute(CommandSender sender, String[] args, Surf plugin);
}