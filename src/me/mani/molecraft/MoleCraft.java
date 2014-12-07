package me.mani.molecraft;

import me.mani.goldenapi.GoldenAPI;
import me.mani.molecraft.config.ConfigManager;
import me.mani.molecraft.debug.DebugManager;
import me.mani.molecraft.game.GameManager;
import me.mani.molecraft.game.LocationManager;
import me.mani.molecraft.setup.SetupManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class MoleCraft extends JavaPlugin {
	
	private static MoleCraft pluginInstance;
	
	public static final String TABLE = "molecraft";
	public static final String SETTING_ALIAS = "setting";
	public static final String VALUE_ALIAS = "value";
	
	private ConfigManager configManager;
	private LocationManager locationManager;
	private SetupManager setupManager;
	private StatsManager statsManager;
	
	@Override
	public void onEnable() {
		
		pluginInstance = this;
		
		// GoldenAPI
		
		GoldenAPI.connectToSQL(this);
		GoldenAPI.setPlayerNameWatching(true);
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		configManager = new ConfigManager();
		configManager.synchronize();
			
		// LocationManager | id = 1
		
		locationManager = new LocationManager(1);
		
		if (configManager.getDebugMode()) {
			
			// Start Debugging
			
			new DebugManager();
			return;
		}
		
		// Start Lobbyphase
		
		for (World w : Bukkit.getWorlds())
			w.setAutoSave(false);
		
		locationManager.loadAll();
		
		setupManager = new SetupManager();
		setupManager.setup();
		
		statsManager = new StatsManager();
		statsManager.setupStatsBoard();
		
		Thread autoRestart = new Thread(new AutoRestartThread());
		autoRestart.start();
		
		new GameManager();
	}
	
	@Override
	public void onDisable() {
		
		if (!configManager.getDebugMode())
			statsManager.sendAll();
		else
			locationManager.saveAll();	
		
	}
	
	public static MoleCraft getInstance() {
		return pluginInstance;
	}
	
	public class AutoRestartThread implements Runnable {

		@Override
		public void run() {
			
			// Sleep one hour and then restart the server automatic
			
			try {
				Thread.sleep(1000 * 60 * 60);
				if (GameState.getGameState() != GameState.LOBBY)
					return;
				Bukkit.broadcastMessage("§cNach 1 Stunde ohne Spiel gibt es einen Restart!");
				Thread.sleep(1000 * 5);
				getInstance().getServer().shutdown();
			} catch (InterruptedException e) {
				getInstance().getServer().shutdown();
			}
		}
		
	}
}
