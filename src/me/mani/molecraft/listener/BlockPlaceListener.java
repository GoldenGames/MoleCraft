package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.SpecialItems;
import me.mani.molecraft.SpecialItems.SpecialItemType;
import me.mani.molecraft.manager.BlockManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends MoleCraftListener {
	
	public BlockPlaceListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		
		else if (GameState.getGameState() == GameState.INGAME) {
			
			if (MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).isSpectator() || !BlockManager.isPlaceable(ev.getBlock().getType()))
				ev.setCancelled(true);
			else if (ev.getBlock().getType() == Material.REDSTONE_TORCH_ON)
				SpecialItems.handleSpecialItem(SpecialItemType.MINI_EXPLOSION, ev.getBlock().getLocation());
			
		}
		
		else
			ev.setCancelled(true);
	}

}
