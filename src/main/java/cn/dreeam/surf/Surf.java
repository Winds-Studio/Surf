package cn.dreeam.surf;

import cn.dreeam.surf.command.CommandHandler;
import cn.dreeam.surf.modules.ConnectionEvent;
import cn.dreeam.surf.modules.IllegalBlockCheck;
import cn.dreeam.surf.modules.NetherCheck;
import cn.dreeam.surf.modules.antiillegal.CleanIllegal;
import cn.dreeam.surf.modules.antilag.BlockPhysics;
import cn.dreeam.surf.modules.antilag.MinecartLag;
import cn.dreeam.surf.modules.antilag.WitherSpawn;
import cn.dreeam.surf.modules.patches.BookBan;
import cn.dreeam.surf.modules.patches.BucketEvent;
import cn.dreeam.surf.modules.patches.ChunkBan;
import cn.dreeam.surf.modules.patches.DispenserCrash;
import cn.dreeam.surf.modules.patches.GateWay;
import cn.dreeam.surf.modules.patches.IllegalDamageAndPotionCheck;
import cn.dreeam.surf.modules.patches.NBTBan;
import cn.dreeam.surf.modules.patches.Offhand;
import cn.dreeam.surf.util.ConfigCache;
import cn.dreeam.surf.util.SecondPassEvent;
import cn.dreeam.surf.util.TenSecondPassEvent;
import com.tcoded.folialib.FoliaLib;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class Surf extends JavaPlugin {

    public static Logger LOGGER;
    private static Surf instance;
    private final PluginManager pluginManager = getServer().getPluginManager();
    private final CommandHandler commandHandler = new CommandHandler(this);
    public FoliaLib foliaLib = new FoliaLib(this);
    private BukkitAudiences adventure;

    public static Surf getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        LOGGER = LogManager.getLogger(instance.getName());
        instance.adventure = BukkitAudiences.create(instance);

        instance.loadConfig();
        commandHandler.registerCommands(); // register commands
        instance.registerEvents(); // register event
        new Metrics(instance, 16810);

        if (ConfigCache.AntiillegalDeleteStackedTotem) {
            foliaLib.getImpl().runTimer(() -> Bukkit.getWorlds().forEach(b -> b.getPlayers().forEach(e -> e.getInventory().forEach(this::revert))), 0L, 20L);
        }
        // Server specific events
        foliaLib.getImpl().runTimer(() -> pluginManager.callEvent(new SecondPassEvent()), 1, 1, TimeUnit.SECONDS);
        foliaLib.getImpl().runTimer(() -> pluginManager.callEvent(new TenSecondPassEvent()), 1, 10, TimeUnit.SECONDS);

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
        // AntiIllegal
        pluginManager.registerEvents(new CleanIllegal(), this);

        // AntiLag
        if (ConfigCache.LimitLiquidSpreadEnabled) pluginManager.registerEvents(new BlockPhysics(), this);
        if (ConfigCache.LimitVehicleEnabled) pluginManager.registerEvents(new MinecartLag(this), this);
        if (ConfigCache.LimitWitherSpawnOnLagEnabled) pluginManager.registerEvents(new WitherSpawn(), this);

        // Patches
        pluginManager.registerEvents(new BucketEvent(), this);
        pluginManager.registerEvents(new BookBan(), this);
        //pluginManager.registerEvents(new ChestLagFix(this), this);
        pluginManager.registerEvents(new ChunkBan(), this);
        pluginManager.registerEvents(new DispenserCrash(), this);
        pluginManager.registerEvents(new GateWay(), this);
        if (ConfigCache.CheckIllegalDamage) pluginManager.registerEvents(new IllegalDamageAndPotionCheck(), this);
        if (ConfigCache.AntiNBTBanEnabeld) pluginManager.registerEvents(new NBTBan(), this);
        pluginManager.registerEvents(new Offhand(), this);

        // Misc
        pluginManager.registerEvents(new ConnectionEvent(), this);
        pluginManager.registerEvents(new IllegalBlockCheck(), this);
        pluginManager.registerEvents(new NetherCheck(), this);
    }

    public void loadConfig() {
        saveDefaultConfig();
        ConfigCache.loadConfig();
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    // Original code by moom0o, https://github.com/moom0o/AnarchyExploitFixes
    public void revert(ItemStack item) {
        if (item != null) {
            if (ConfigCache.AntiillegalDeleteStackedTotem && item.getType() == Material.TOTEM_OF_UNDYING && item.getAmount() > item.getMaxStackSize()) {
                item.setAmount(item.getMaxStackSize());
            }
        }
    }

    public @NotNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
}
