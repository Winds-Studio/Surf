package org.surf;

import io.papermc.lib.PaperLib;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.surf.command.CommandHandler;
import org.surf.command.NotInPluginYMLException;
import org.surf.modules.IllegalBlockCheck;
import org.surf.modules.*;
import org.surf.modules.antiillegal.*;
import org.surf.modules.antilag.*;
import org.surf.modules.patches.*;
import org.surf.util.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin {

	public static Main instance;

	public static Main getInstance() {
		return instance;
	}

	private final PluginManager pluginManager = getServer().getPluginManager();

	private final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

	private final CommandHandler commandHandler = new CommandHandler(this);

	public void onEnable() {
		instance = this;
		// TODO: config system
		this.loadConfig();
		int pluginId = 16810;
		new Metrics(this, pluginId);

		// register commands
		try {
			commandHandler.registerCommands();
		} catch (NotInPluginYMLException e) {
			e.printStackTrace();
		}
		// register event
		this.registerEvents();
		//Alert system events
		PaperLib.suggestPaper(this);
		//Server specific events
		service.scheduleAtFixedRate(() -> pluginManager.callEvent(new SecondPassEvent()), 1, 1, TimeUnit.SECONDS);
		service.scheduleAtFixedRate(() -> pluginManager.callEvent(new TenSecondPassEvent()), 1, 10, TimeUnit.SECONDS);
		getLogger().info("Surf enabled. By Dreeam.");
	}

	public void registerEvents() {
		pluginManager.registerEvents(new IllegalBlockCheck(), this);
		pluginManager.registerEvents(new Offhand(), this);
		if (PaperLib.isPaper()) {
			pluginManager.registerEvents(new GateWay(), this);
		}
		pluginManager.registerEvents(new BookBan(), this);
		pluginManager.registerEvents(new ChunkBan(), this);
		pluginManager.registerEvents(new NetherCheck(), this);
		pluginManager.registerEvents(new IllegalDamageAndPotionCheck(), this);
		pluginManager.registerEvents(new WitherSpawn(), this);
		pluginManager.registerEvents(new BlockPhysics(), this);
		pluginManager.registerEvents(new BucketEvent(this), this);
		pluginManager.registerEvents(new MinecartLag(this), this);
//		pluginManager.registerEvents(new ChestLagFix(this), this);
		pluginManager.registerEvents(new Dispensor(this), this);
		pluginManager.registerEvents(new ConnectionEvent(this), this);
		// AntiIllegal events
		pluginManager.registerEvents(new CleanIllegal(this), this);
	}

	public void loadConfig() {
		saveDefaultConfig();
		ConfigCache.loadConfig();
	}

	public void onDisable() {
		getLogger().info("Surf disabled. By Dreeam");
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
