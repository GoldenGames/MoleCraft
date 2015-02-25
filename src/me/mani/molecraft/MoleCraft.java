package me.mani.molecraft;

import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.SetupManager;
import me.mani.molecraft.manager.StatsManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class MoleCraft extends JavaPlugin {
	
	private static MoleCraft pluginInstance;
	
	public static final String TABLE = "molecraft";
	public static final String SETTING_ALIAS = "setting";
	public static final String VALUE_ALIAS = "value";
	
	public GameManager gameManager;
	
	private SetupManager setupManager;
	private StatsManager statsManager;
	
	@Override
	public void onEnable() {
		
		pluginInstance = this;
			
		getConfig().addDefault("debug", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
					
		if (getConfig().getBoolean("debug")) {
			
			// Start Debugging
			new DebugManager(this);
			return;
		}
		
		// Actually starts setup followed by game
		
		for (World w : Bukkit.getWorlds())
			w.setAutoSave(false);
		
		setupManager = new SetupManager(this);
		if (setupManager.setup())
			gameManager = new GameManager(setupManager);
//		else
			// TODO: Offline, needs checking 
		
//		statsManager = new StatsManager();
//		statsManager.setupStatsBoard();
		
		Thread autoRestart = new Thread(new AutoRestartThread());
		autoRestart.start();
	}
	
	@Override
	public void onDisable() {
		
		if (!getConfig().getBoolean("debug"))
			statsManager.sendAll();
		
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
				Bukkit.broadcastMessage("§cDer Server wird aus Datenbankgründen nach einer Stunde automatisch neugestartet!");
				Thread.sleep(1000 * 5);
				getInstance().getServer().shutdown();
			} catch (InterruptedException e) { return; }
		}
		
	}
}
