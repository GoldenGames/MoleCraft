package me.mani.molecraft;

import me.mani.molecraft.ArenaMapPack.ArenaMapInfo;
import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MoleCraft extends JavaPlugin {
	
	private static MoleCraft pluginInstance;
	
	public static final String TABLE = "molecraft";
	public static final String SETTING_ALIAS = "setting";
	public static final String VALUE_ALIAS = "value";
	
	public DebugManager debugManager;
	public GameManager gameManager;
	
	private Thread autoRestartThread;
	
	@Override
	public void onEnable() {
		pluginInstance = this;
		Messenger.setPrefix("§8[§aMoleCraft§8] §7");
		Messenger.setSuffix("§a");
		
		getConfig().addDefault("debug", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
				
		Bukkit.createWorld(new WorldCreator("map")).setDifficulty(Difficulty.EASY);
		for (World w : Bukkit.getWorlds()) {
			w.setAutoSave(false);
			w.setSpawnFlags(false, false);
		}
		
		ConfigurationSerialization.registerClass(ArenaMapInfo.class, "ArenaMapInfo");
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		if (isDebug())
			debugManager = new DebugManager(this);
		else {
			gameManager = new GameManager(this);
			gameManager.startBootstrap();
		}
		
		getCommand("close").setExecutor(new CommandExecutor() {
			
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				try {
					for (Player player : Bukkit.getOnlinePlayers())
						BungeeCordHandler.connect(player);
					}
				catch (Exception e) {
					e.printStackTrace();
				}
				Bukkit.getScheduler().runTaskLater(pluginInstance, () -> getServer().shutdown(), 20L * 5);
				return true;
			}
			
		});
		
		autoRestartThread = new Thread(new AutoRestartThread());
		autoRestartThread.start();
	}
	
	@Override
	public void onDisable() {
				
		if (!isDebug())
			gameManager.statsManager.sendAll();
		
	}
	
	public boolean isDebug() {
		return getConfig().getBoolean("debug");
	}
	
	public static MoleCraft getInstance() {
		return pluginInstance;
	}
	
	public void cancelAutoRestart() {
		autoRestartThread.interrupt();
	}
	
	public class AutoRestartThread implements Runnable {

		@Override
		public void run() {
			
			// Sleep one hour and then restart the server automatic	
			try {
				Thread.sleep(1000 * 60 * 60 * 4);
				Bukkit.broadcastMessage("§cDer Server wird aus Datenbankgründen nach vier Stunde automatisch neugestartet!");
				Thread.sleep(1000 * 5);
				getInstance().getServer().shutdown();
			} catch (InterruptedException e) { return; }
			
		}
		
	}
}
