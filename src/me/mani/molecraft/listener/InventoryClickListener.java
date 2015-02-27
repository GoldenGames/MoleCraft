package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener extends MoleCraftListener {
	
	public InventoryClickListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		
	}

}
