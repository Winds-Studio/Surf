package org.surf.command.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.surf.Main;
import org.surf.command.BaseCommand;

public class UUidCommand extends BaseCommand {

    public UUidCommand() {
        super(
                "uuid",
                "/uuid <player>",
                "l2x9core.command.uuid",
                "Get the uuid of the specified player");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        if (args.length > 0) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            sendClickableMessage(
                    sender,
                    "&6The UUID of&r&c " + target.getName() + "&r&6 is &r&c" + target.getUniqueId().toString(),
                    "&a&l&lClick to copy",
                    target.getUniqueId().toString(),
                    ClickEvent.Action.SUGGEST_COMMAND
            );

        } else {
            sendErrorMessage(sender, "Please include at least one argument /uuid <playerName>");
        }
    }

    private void sendClickableMessage(CommandSender sender, String message, String hoverText, String command, ClickEvent.Action action) {
        TextComponent msg = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
        msg.setClickEvent(new ClickEvent(action, command));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', hoverText))
                        .create()));
        sender.spigot().sendMessage(msg);
    }
}
