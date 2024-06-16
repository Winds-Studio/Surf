package cn.dreeam.surf;

import cn.dreeam.surf.command.CommandHandler;
import cn.dreeam.surf.config.Config;
import cn.dreeam.surf.config.ConfigManager;
import cn.dreeam.surf.modules.antiillegal.CheckIllegal;
import cn.dreeam.surf.modules.antiillegal.CheckRoseStackerItem;
import cn.dreeam.surf.modules.antiillegal.IllegalBlockCheck;
import cn.dreeam.surf.modules.antiillegal.IllegalDamageAndPotionCheck;
import cn.dreeam.surf.modules.antiillegal.StackedTotem;
import cn.dreeam.surf.modules.antilag.BlockPhysics;
import cn.dreeam.surf.modules.antilag.MinecartLag;
import cn.dreeam.surf.modules.antilag.Offhand;
import cn.dreeam.surf.modules.antilag.WitherSpawn;
import cn.dreeam.surf.modules.misc.ConnectionEvent;
import cn.dreeam.surf.modules.misc.NetherCheck;
import cn.dreeam.surf.modules.patch.BookBan;
import cn.dreeam.surf.modules.patch.BucketEvent;
import cn.dreeam.surf.modules.patch.ChunkBan;
import cn.dreeam.surf.modules.patch.DispenserCrash;
import cn.dreeam.surf.modules.patch.GateWay;
import cn.dreeam.surf.modules.patch.NBTBan;
import cn.dreeam.surf.modules.patch.PreventEnderPearlClip;
import com.tcoded.folialib.FoliaLib;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Surf extends JavaPlugin {

    private static Surf instance;
    public static Logger LOGGER;

    public ConfigManager<Config> configManager;
    public static Config config;
    private final PluginManager pluginManager = getServer().getPluginManager();
    private final CommandHandler commandHandler = new CommandHandler(this);

    public FoliaLib foliaLib = new FoliaLib(this);
    private BukkitAudiences adventure;

    // Dreeam - e.g. 1.20.2-R0.1-SNAPSHOT -> 20, replace string before first decimal point, then replace all string after the second decimal point
    public static final int majorVersion = Integer.parseInt(Bukkit.getServer().getBukkitVersion()
            .replaceFirst("^(\\d+)\\.", "")
            .replaceAll("\\.(.+)", "")
    );

    public boolean isRoseStackerEnabled = false;

    @Override
    public void onEnable() {
        instance = this;
        LOGGER = LogManager.getLogger(instance.getName());
        instance.adventure = BukkitAudiences.create(instance);

        instance.loadConfig();
        commandHandler.registerCommands(); // register commands
        instance.registerEvents(); // register event
        new Metrics(instance, 16810);

        LOGGER.info("Surf {} enabled. By Dreeam.", instance.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        LOGGER.info("Surf {} disabled. By Dreeam", instance.getDescription().getVersion());
    }

    public void registerEvents() {
        List<Listener> listeners = Arrays.asList(
                // CheckIllegal
                new CheckIllegal(),
                new IllegalBlockCheck(),
                new IllegalDamageAndPotionCheck(),
                new StackedTotem(),

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
            pluginManager.registerEvents(new CheckRoseStackerItem(), instance);
        }
    }

    public void loadConfig() {
        configManager = ConfigManager.create(instance.getDataFolder().toPath(), "config.yml", Config.class);
        configManager.reloadConfig();
        config = configManager.getConfigData();
    }

    public static Surf getInstance() {
        return instance;
    }
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public @NotNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
}
