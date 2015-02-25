package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.BlockManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends MoleCraftListener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		
		else if (GameState.getGameState() == GameState.INGAME) {
			
			if (!BlockManager.isPlaceable(ev.getBlock().getType()))
				ev.setCancelled(true);
			
		}
		
		else
			ev.setCancelled(true);
	}

}
