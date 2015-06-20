package me.mani.molecraft;

import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.Listener;

public class MoleCraftListener implements Listener {
	
	protected GameManager gameManager;
	protected DebugManager debugManager;
	
	public MoleCraftListener(MainManager mainManager) {
		MoleCraft.getInstance().getServer().getPluginManager().registerEvents(this, MoleCraft.getInstance());
		if (mainManager instanceof GameManager)
			this.gameManager = (GameManager) mainManager;
		else if (mainManager instanceof DebugManager)
			this.debugManager = (DebugManager) mainManager;
	}
	
}
