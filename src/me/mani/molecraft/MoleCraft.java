package me.mani.molecraft;

import me.mani.molecraft.ArenaMapPack.ArenaMapInfo;
import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class MoleCraft extends JavaPlugin {
	
	private static MoleCraft pluginInstance;
	
	public static final String TABLE = "molecraft";
	public static final String SETTING_ALIAS = "setting";
	public static final String VALUE_ALIAS = "value";
	
	public DebugManager debugManager;
	public GameManager gameManager;
	
//	private StatsManager statsManager;
	
	@Override
	public void onEnable() {
		pluginInstance = this;
		Messenger.setPrefix("§7<§aMoleCraft§7> §e");
		
		getConfig().addDefault("debug", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Bukkit.createWorld(new WorldCreator("map")).setDifficulty(Difficulty.EASY);
		for (World w : Bukkit.getWorlds()) {
			w.setAutoSave(false);
			w.setSpawnFlags(false, false);
		}
		
		ConfigurationSerialization.registerClass(ArenaMapInfo.class, "ArenaMapInfo");
		
		if (isDebug())
			debugManager = new DebugManager(this);
		else {
			gameManager = new GameManager(this);
			gameManager.startBootstrap();
		}
		
//		statsManager = new StatsManager();
//		statsManager.setupStatsBoard();
		
		Thread autoRestart = new Thread(new AutoRestartThread());
		autoRestart.start();
	}
	
	@Override
	public void onDisable() {
				
//		if (!isDebug())
//			statsManager.sendAll();
		
	}
	
	public boolean isDebug() {
		return getConfig().getBoolean("debug");
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
