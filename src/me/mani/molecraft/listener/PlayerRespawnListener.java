package me.mani.molecraft.listener;

import me.mani.molecraft.game.LocationManager.SpecialLocation;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener extends AdvListener {
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent ev) {
		
		ev.setRespawnLocation(SpecialLocation.LOBBY_SPAWN);
		
	}

}
