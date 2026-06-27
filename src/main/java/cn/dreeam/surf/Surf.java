package cn.dreeam.surf;

import cn.dreeam.surf.command.CommandHandler;
import cn.dreeam.surf.config.ConfigManager;
import cn.dreeam.surf.listener.*;
import cn.dreeam.surf.listener.hook.*;
import cn.dreeam.surf.modules.misc.ConnectionEvent;
import cn.dreeam.surf.modules.misc.NetherCheck;
import cn.dreeam.surf.modules.misc.antilag.BlockPhysics;
import cn.dreeam.surf.modules.misc.antilag.MinecartLag;
import cn.dreeam.surf.modules.misc.antilag.Offhand;
import cn.dreeam.surf.modules.misc.antilag.WitherSpawn;
import cn.dreeam.surf.modules.misc.patch.BookBan;
import cn.dreeam.surf.modules.misc.patch.BucketEvent;
import cn.dreeam.surf.modules.misc.patch.ChunkBan;
import cn.dreeam.surf.modules.misc.patch.DispenserCrash;
import cn.dreeam.surf.modules.misc.patch.GateWay;
import cn.dreeam.surf.modules.misc.patch.NBTBan;
import cn.dreeam.surf.modules.misc.patch.PreventEnderPearlClip;
import com.tcoded.folialib.FoliaLib;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Surf extends JavaPlugin {

    private static Surf instance;
    public static Logger LOGGER;

    private static ConfigManager configManager;
    private final PluginManager pluginManager = getServer().getPluginManager();
    private final CommandHandler commandHandler = new CommandHandler(this);

    public FoliaLib foliaLib = new FoliaLib(this);

    public boolean isRoseStackerEnabled = false;

    @Override
    public void onEnable() {
        instance = this;
        LOGGER = LogManager.getLogger(instance.getName());

        loadConfig();
        commandHandler.registerCommands(); // register commands
        registerEvents(); // register event
        new Metrics(instance, 16810);

        LOGGER.info("Surf {} enabled. By Dreeam.", instance.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        LOGGER.info("Surf {} disabled. By Dreeam", instance.getDescription().getVersion());
    }

    public void registerEvents() {
        List<Listener> listeners = Arrays.asList(
                // CheckIllegal
                new ListenerBasic(),
                new ListenerBlock(),
                new ListenerDamage(),
                new ListenerPotion(),
                new ListenerStackedTotem(),

                // AntiLag
                new BlockPhysics(),
                new MinecartLag(),
                new Offhand(),
                new WitherSpawn(),

                // Misc
                new ConnectionEvent(),
                new NetherCheck(),

                // Patches
                new BookBan(),
                new BucketEvent(),
                new ChunkBan(),
                new DispenserCrash(),
                new GateWay(),
                new NBTBan(),
                new PreventEnderPearlClip()
        );

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, instance);
        }

        if (getServer().getPluginManager().getPlugin("RoseStacker") != null) {
            isRoseStackerEnabled = true;
            pluginManager.registerEvents(new ListenerRoseStackerItem(), instance);
        }
    }

    public void createDirectory(File dir) throws IOException {
        try {
            Files.createDirectories(dir.toPath());
        } catch (FileAlreadyExistsException e) { // Thrown if dir exists but is not a directory
            if (dir.delete()) createDirectory(dir);
        }
    }

    public void loadConfig() {
        try {
            createDirectory(getDataFolder());
            configManager = new ConfigManager();
            configManager.saveConfig();
        } catch (Exception e) {
            LOGGER.error("Failed to load config file!", e);
        }
    }

    public static Surf getInstance() {
        return instance;
    }
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
    public static ConfigManager configManager() {
        return configManager;
    }
}
