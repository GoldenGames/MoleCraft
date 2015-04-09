package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerItemDropPickupListener extends MoleCraftListener {

	public PlayerItemDropPickupListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY || GameState.getGameState() == GameState.WARM_UP)
			ev.setCancelled(true);
		
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent ev) {

		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		else if (GameState.getGameState() == GameState.INGAME) {
			if (ev.getItem().getItemStack().getAmount() > 1)
				ev.getItem().remove();
			if (ev.getItem().getItemStack().getType() == Material.DIRT 
			&& (ev.getPlayer().getInventory().containsAtLeast(ev.getItem().getItemStack(), 64)
			|| ev.getPlayer().getItemOnCursor().getType() != Material.AIR))
				ev.setCancelled(true);
		}
		
	}
}
