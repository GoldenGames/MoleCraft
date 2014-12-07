package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerItemDropPickupListener extends AdvListener {

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent ev) {
		
		if (getState() == GameState.LOBBY)
			ev.setCancelled(true);
		
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent ev) {
		
		if (getState() == GameState.LOBBY)
			ev.setCancelled(true);
		
	}
	
}
