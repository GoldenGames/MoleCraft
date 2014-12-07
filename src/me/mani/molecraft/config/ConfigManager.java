package me.mani.molecraft.config;

import me.mani.molecraft.Manager;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager extends Manager {
	
	private boolean debugMode;
	
	public void synchronize() {
		FileConfiguration config = getPlugin().getConfig();
		
		config.addDefault("debug", false);
		
		debugMode = config.getBoolean("debug");
		
		config.options().copyDefaults(true);
		getPlugin().saveConfig();
	}
	
	public boolean getDebugMode() {
		return debugMode;
	}

}
