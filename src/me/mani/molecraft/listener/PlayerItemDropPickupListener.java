package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerItemDropPickupListener extends MoleCraftListener {

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		
	}
}
