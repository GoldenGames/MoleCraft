package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.config.BlockManager;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends AdvListener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent ev) {
		
		if (getState() == GameState.LOBBY)
			ev.setCancelled(true);
		
		else if (getState() == GameState.INGAME) {
			
			if (!BlockManager.isPlaceable(ev.getBlock().getType()))
				ev.setCancelled(true);
			
		}
		
		else
			ev.setCancelled(true);
	}

}
