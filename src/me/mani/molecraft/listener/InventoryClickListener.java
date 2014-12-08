package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener extends AdvListener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent ev) {
		
		if (getState() == GameState.LOBBY)
			ev.setCancelled(true);
		
	}

}
