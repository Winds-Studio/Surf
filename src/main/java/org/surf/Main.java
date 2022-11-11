package org.surf;

import io.papermc.lib.PaperLib;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.surf.command.CommandHandler;
import org.surf.command.NotInPluginYMLException;
import org.surf.modules.BlockPlace;
import org.surf.modules.*;
import org.surf.modules.antiillegal.*;
import org.surf.modules.antilag.*;
import org.surf.modules.patches.*;
import org.surf.util.Metrics;
import org.surf.util.SecondPassEvent;
import org.surf.util.TenSecondPassEvent;
import org.surf.util.Utils;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin {

	public static Main instance;

	public static Main getInstance() {
		return instance;
	}

	private final PluginManager pluginManager = getServer().getPluginManager();

	private final HashMap<String, Integer> entityIntegerHashMap = new HashMap<>();
	ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

	public CommandHandler commandHandler;
	public final Queue<String> discordAlertQueue = new LinkedList<>();

	public void onEnable() {
		instance = this;

		saveDefaultConfig();
		commandHandler = new CommandHandler(this);
		int pluginId = 16810;
		new Metrics(this, pluginId);

		pluginManager.registerEvents(new BlockPlace(this), this);
		pluginManager.registerEvents(new Offhand(this), this);
		if (PaperLib.isPaper()) {
			pluginManager.registerEvents(new GateWay(this), this);
		}
		try {
			commandHandler.registerCommands();
		} catch (NotInPluginYMLException e) {
			e.printStackTrace();
		}
		pluginManager.registerEvents(new BookBan(), this);
		pluginManager.registerEvents(new ChunkBan(this), this);
		pluginManager.registerEvents(new MoveEvent(this), this);
		pluginManager.registerEvents(new EntityDamageEvent(this), this);
		pluginManager.registerEvents(new WitherSpawn(), this);
		pluginManager.registerEvents(new BlockPhysics(this), this);
		pluginManager.registerEvents(new BucketEvent(this), this);
		pluginManager.registerEvents(new MinecartLag(this), this);
//		pluginManager.registerEvents(new ChestLagFix(this), this);
		pluginManager.registerEvents(new Dispensor(this), this);
		pluginManager.registerEvents(new ConnectionEvent(this), this);
		// AntiIllegal events
		pluginManager.registerEvents(new CleanIllegal(this), this);
		//Alert system events
		PaperLib.suggestPaper(this);
		//Server specific events
		service.scheduleAtFixedRate(() -> pluginManager.callEvent(new SecondPassEvent()), 1, 1, TimeUnit.SECONDS);
		service.scheduleAtFixedRate(() -> pluginManager.callEvent(new TenSecondPassEvent()), 1, 10, TimeUnit.SECONDS);
		getLogger().info("Surf enabled. By Dreeam.");
	}

	public void onDisable() {
		getLogger().info("Surf disabled. By Dreeam");
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
