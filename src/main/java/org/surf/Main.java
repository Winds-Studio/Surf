package org.surf;

import com.tcoded.folialib.FoliaLib;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.surf.command.CommandHandler;
import org.surf.command.NotInPluginYMLException;
import org.surf.modules.ConnectionEvent;
import org.surf.modules.IllegalBlockCheck;
import org.surf.modules.NetherCheck;
import org.surf.modules.antiillegal.CleanIllegal;
import org.surf.modules.antilag.BlockPhysics;
import org.surf.modules.antilag.MinecartLag;
import org.surf.modules.antilag.WitherSpawn;
import org.surf.modules.patches.BookBan;
import org.surf.modules.patches.BucketEvent;
import org.surf.modules.patches.ChunkBan;
import org.surf.modules.patches.DispenserCrash;
import org.surf.modules.patches.GateWay;
import org.surf.modules.patches.IllegalDamageAndPotionCheck;
import org.surf.modules.patches.NBTBan;
import org.surf.modules.patches.Offhand;
import org.surf.util.ConfigCache;
import org.surf.util.SecondPassEvent;
import org.surf.util.TenSecondPassEvent;

import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin {

	private static Main instance;
	private BukkitAudiences adventure;

	public static Main getInstance() {
		return instance;
	}

	private final PluginManager pluginManager = getServer().getPluginManager();

	public FoliaLib foliaLib = new FoliaLib(this);

	private final CommandHandler commandHandler = new CommandHandler(this);

	public void onEnable() {
		instance = this;
		this.adventure = BukkitAudiences.create(this);
		// TODO: config system
		this.loadConfig();
		new Metrics(this, 16810);

		// register commands
		try {
			commandHandler.registerCommands();
		} catch (NotInPluginYMLException e) {
			e.printStackTrace();
		}
		// register event
		this.registerEvents();
		if (ConfigCache.AntiillegalDeleteStackedTotem) {
			foliaLib.getImpl().runTimer(() -> Bukkit.getWorlds().forEach(b -> b.getPlayers().forEach(e -> e.getInventory().forEach(this::revert))), 0L, 20L);
		}
		// Server specific events
		foliaLib.getImpl().runTimer(() -> pluginManager.callEvent(new SecondPassEvent()), 1, 1, TimeUnit.SECONDS);
		foliaLib.getImpl().runTimer(() -> pluginManager.callEvent(new TenSecondPassEvent()), 1, 10, TimeUnit.SECONDS);
		getLogger().info("Surf Enabled. By Dreeam.");
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

	public void onDisable() {
		if (this.adventure != null) {
			this.adventure.close();
			this.adventure = null;
		}
		getLogger().info("Surf disabled. By Dreeam");
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
