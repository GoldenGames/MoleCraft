package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener extends MoleCraftListener {
	
	public PlayerRespawnListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent ev) {
		
		ev.setRespawnLocation(gameManager.locationManager.LOBBY_SPAWN);
		
	}

}
