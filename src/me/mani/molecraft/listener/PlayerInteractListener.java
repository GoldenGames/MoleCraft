package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.SpecialItems;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener extends MoleCraftListener {

	public PlayerInteractListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		
		Player p = ev.getPlayer();

		// Checks if the item is used in lobby phase, if so it will be continued
		if (GameState.getGameState() == GameState.LOBBY) {

			if (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ItemStack item = ev.getItem();
				if (item == null || item.getType() == null)
					return;

				// The book item opens the voting menu
				if (item.getType() == Material.BOOK)
					gameManager.inventoryManager.openVotingInventory(p);

				else if (item.getType() == Material.WRITTEN_BOOK)
					return;

				// The firework item teleports the player to the parkour
				else if (item.getType() == Material.FIREWORK)
					p.teleport(gameManager.locationManager.PARKOUR_SPAWN);
			}
		}
		
		else if (GameState.getGameState() == GameState.INGAME) {
			
			if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.ENDER_CHEST)
				SpecialItems.openSpecialChest(p);
			else if (ev.getItem() != null && ev.getItem().getType() == Material.COMPASS)
				gameManager.inventoryManager.openSpectatorInventory(p);
			else
				return;
			
		}
		
		ev.setCancelled(true);
	}
}
