package me.mani.molecraft.util;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraft;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.avaje.ebeaninternal.server.core.Message;

public class AdvListener extends Message implements Listener {
		
	public AdvListener() {
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	public MoleCraft getPlugin() {
		return MoleCraft.getInstance();
	}
	
	public GameState getState() {
		return GameState.getGameState();
	}	
}
