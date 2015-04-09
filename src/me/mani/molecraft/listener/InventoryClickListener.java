package me.mani.molecraft.listener;

import java.util.ArrayList;
import java.util.List;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener extends MoleCraftListener {
	
	public InventoryClickListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent ev) {
		
		Player player = (Player) ev.getWhoClicked();
		ItemStack itemStack = ev.getCurrentItem();
		
		if (GameState.getGameState() == GameState.LOBBY) {
			
			if (itemStack != null && itemStack.getType() == Material.STAINED_CLAY) {
				int choiseId =  itemStack.getDurability() - 1;
				if (gameManager.lobbyManager.getVoteManager().addVoting(player, choiseId)) {
					player.closeInventory();
					ItemMeta itemMeta = itemStack.getItemMeta();
					List<String> displayLore = new ArrayList<>(itemMeta.getLore());
					displayLore.set(displayLore.size() - 1, "§e" + gameManager.lobbyManager.getVoteManager().getVotings(choiseId) + " Votings");
					itemMeta.setLore(displayLore);
					itemStack.setItemMeta(itemMeta);
					Messenger.send(player, "Du hast erfolgreich gevotet.");
				}
				else
					Messenger.send(player, "Du hast bereits gevotet.");
			}
			ev.setCancelled(true);
		}
		
	}

}
