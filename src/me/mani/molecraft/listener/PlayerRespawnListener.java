package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener extends MoleCraftListener {
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent ev) {
		
		ev.setRespawnLocation(gameManager.locationManager.LOBBY_SPAWN);
		
	}

}
