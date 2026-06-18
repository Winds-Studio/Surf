package cn.dreeam.surf.command.commands;

import cn.dreeam.surf.Surf;
import cn.dreeam.surf.util.MessageUtil;
import cn.dreeam.surf.util.Util;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.NBTHandler;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.logging.Logger;

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
                case "reload": {
                    Surf.getInstance().loadConfig();
                    MessageUtil.sendMessage(sender, Util.getPrefix() + "&aReloaded configuration file");
                    break;
                }
                case "version": {
                    MessageUtil.sendMessage(sender, Util.getPrefix() + "&6Version &r&c" + plugin.getDescription().getVersion());
                    break;
                }
                case "help": {
                    MessageUtil.sendMessage(sender, Util.getPrefix() + "&1---&r " + Util.getPrefix() + "&6Help &r&1---");
                    MessageUtil.sendMessage(sender, Util.getPrefix());
                    plugin.getCommandHandler().getCommands().forEach(command -> {
                        String helpMsg = "&1---&r&3&l /" + command.getName() + "&r&6 Help &r&1---";
                        MessageUtil.sendMessage(sender, Util.getPrefix() + helpMsg);
                        MessageUtil.sendMessage(sender, Util.getPrefix() + "&3Description: " + command.getDescription());
                        if (command.getSubCommands() != null) {
                            if (command.getSubCommands().length > 0) {
                                MessageUtil.sendMessage(sender, Util.getPrefix() + helpMsg.replace("Help", "Subcommands"));
                                for (String subCommand : command.getSubCommands()) {
                                    String[] split = subCommand.split("::");
                                    if (split.length > 0) {
                                        MessageUtil.sendMessage(sender, Util.getPrefix() + "&6 /" + command.getName() + " " + split[0] + " |&r&e " + split[1]);
                                    } else {
                                        MessageUtil.sendMessage(sender, Util.getPrefix() + "&6 /" + command.getName() + " " + subCommand);
                                    }
                                }
                                MessageUtil.sendMessage(sender, Util.getPrefix() + "&1--------------------");
                            }
                        }
                    });
                    break;
                }
                case "test": {
                    debug(sender);
                }
            }
        } else {
            sendErrorMessage(sender, getUsage());
        }
    }

    private static void debug(CommandSender sender) {
        // Debug
        if (sender instanceof Player player) {
//            final String itemStr = """
//                    minecraft:diamond_sword[enchantments={"minecraft:looting": 1, "minecraft:sharpness": 3, "minecraft:unbreaking": 2},custom_data={PublicBukkitValues: {"enchantedcore:enchantmax": 4}},tooltip_display={hidden_components: ["minecraft:attribute_modifiers", "minecraft:enchantments"]},custom_name={extra: [{italic: 0b, underlined: 0b, bold: 0b, color: "dark_purple", obfuscated: 0b, strikethrough: 0b, text: "Enchanted Sword"}], text: ""},lore=[{extra: [" "], text: ""}, {extra: [{italic: 0b, underlined: 0b, bold: 0b, color: "#97B7D1", obfuscated: 0b, strikethrough: 0b, text: "+ "}, {italic: 0b, underlined: 0b, bold: 0b, color: "gray", obfuscated: 0b, strikethrough: 0b, text: "Looting "}, {italic: 0b, text: "I", color: "white"}], text: ""}, {extra: [{italic: 0b, underlined: 0b, bold: 0b, color: "#97B7D1", obfuscated: 0b, strikethrough: 0b, text: "+ "}, {italic: 0b, underlined: 0b, bold: 0b, color: "gray", obfuscated: 0b, strikethrough: 0b, text: "Sharpness "}, {italic: 0b, text: "III", color: "white"}], text: ""}, {extra: [{italic: 0b, underlined: 0b, bold: 0b, color: "#97B7D1", obfuscated: 0b, strikethrough: 0b, text: "+ "}, {italic: 0b, underlined: 0b, bold: 0b, color: "gray", obfuscated: 0b, strikethrough: 0b, text: "Unbreaking "}, {italic: 0b, text: "II", color: "white"}], text: ""}, {extra: [{italic: 0b, underlined: 0b, bold: 0b, color: "#97B7D1", obfuscated: 0b, strikethrough: 0b, text: "+ "}, {italic: 0b, underlined: 0b, bold: 0b, color: "gray", obfuscated: 0b, strikethrough: 0b, text: "Available Slot"}], text: ""}, {extra: [" "], text: ""}, {extra: [{italic: 0b, underlined: 0b, bold: 0b, color: "#97B7D1", obfuscated: 0b, strikethrough: 0b, text: "Enchant Slots: "}, {italic: 0b, underlined: 0b, bold: 0b, color: "#7370B5", obfuscated: 0b, strikethrough: 0b, text: "3/4"}], text: ""}]]
//                    """;

            final String itemStr = """
                    minecraft:iron_pickaxe[tooltip_display={hidden_components: ["minecraft:attribute_modifiers"]},custom_data={PublicBukkitValues: {"valhallammo:actual_stats": "DIG_SPEED:0.098837:ADD_NUMBER:false;GENERIC_ATTACK_DAMAGE:5.581395:ADD_NUMBER:false;GENERIC_ATTACK_SPEED:1.27907:ADD_NUMBER:false;MINING_SPEED:6.0:ADD_NUMBER:true", "valhallammo:default_stats": "DIG_SPEED:0.0:ADD_NUMBER:false;GENERIC_ATTACK_DAMAGE:4.0:ADD_NUMBER:false;GENERIC_ATTACK_SPEED:1.2:ADD_NUMBER:false;MINING_SPEED:6.0:ADD_NUMBER:true", "valhallammo:durability": 349, "valhallammo:item_flags": "DISPLAY_ATTRIBUTES", "valhallammo:max_durability": 349, "valhallammo:neutral_quality": 110, "valhallammo:quality_smithing": 170, "valhallammo:smithing_treatments": "8:3"}},attribute_modifiers=[{amount: 4.581395d, id: "minecraft:e81d691e-4981-48ba-8d76-71321ed7f116", operation: "add_value", slot: "mainhand", type: "minecraft:attack_damage"}, {amount: -2.72093d, id: "minecraft:74dc0292-7ef7-4b4c-bcb7-91ae759ce548", operation: "add_value", slot: "mainhand", type: "minecraft:attack_speed"}],lore=[{extra: [{bold: 0b, color: "yellow", italic: 0b, obfuscated: 0b, strikethrough: 0b, text: "Upgrades: 3", underlined: 0b}], text: ""}, {extra: [{bold: 0b, color: "yellow", italic: 0b, obfuscated: 0b, strikethrough: 0b, text: "Flawless", underlined: 0b}], text: ""}, {extra: [{bold: 0b, color: "white", italic: 0b, obfuscated: 0b, strikethrough: 0b, text: " ", underlined: 0b}, {color: "gray", italic: 0b, text: "5.58 Attack Damage"}], text: ""}, {extra: [{bold: 0b, color: "white", italic: 0b, obfuscated: 0b, strikethrough: 0b, text: " ", underlined: 0b}, {color: "gray", italic: 0b, text: "1.28 Attack Speed"}], text: ""}, {extra: [{bold: 0b, color: "white", italic: 0b, obfuscated: 0b, strikethrough: 0b, text: " ", underlined: 0b}, {color: "gray", italic: 0b, text: "9.9% Mining Speed"}], text: ""}, {extra: [{bold: 0b, color: "gray", italic: 0b, obfuscated: 0b, strikethrough: 0b, text: "Durability: 349/349", underlined: 0b}], text: ""}]]
                    """;

            ItemStack recreatedItemStack = Bukkit.getItemFactory().createItemStack(itemStr.trim());

            ReadWriteNBT nbt = NBT.itemStackToNBT(recreatedItemStack);

            // Debug Toolkit
            // 遍历物品解析出来的根 NBT key
            System.out.println(nbt.getKeys());

            // 遍历实际 component 的所有key
            ReadWriteNBT components = nbt.getCompound("components");
            if (components != null) {
                for (String key : components.getKeys()) {
                    String type = String.valueOf(components.getType(key));
                    System.out.println(key + " -> " + type);
                }
            }

            NBT.modify(recreatedItemStack, $ -> {
                $.removeKey();
            });
        }
    }
}
