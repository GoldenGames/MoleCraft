package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener extends MoleCraftListener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		Player p = ev.getPlayer();
		
		// Checks if the item is used in lobby phase, if so it will be continued
		if (GameState.getGameState() != GameState.LOBBY)
			return;
		
		if (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = ev.getItem();
			if (item == null || item.getType() == null)
				return;
				
			// The paper item gives an explanation of the game		
			if (item.getType() == Material.PAPER) {
				new TutorialThread(p, item).start();
			}
			
			// The firework item teleports the player to the parkour
			if (item.getType() == Material.FIREWORK)
				p.teleport(gameManager.locationManager.PARKOUR_SPAWN);
		}
		ev.setCancelled(true);	
	}

	/**
	 * This thread gives the player an short explanation of the gamemode
	 * 
	 * @author 1999mani
	 *
	 */
	public class TutorialThread extends Thread {
		
		private Player p;
		private ItemStack item;
		
		public TutorialThread(Player p, ItemStack item) {
			this.p = p;
			this.item = item;
		}
		
		@Override
		public void run() {
			try {
				p.getInventory().remove(item);
				Messenger.send(p, "In MoleCraft geht es darum sich durch");
				Messenger.send(p, "einen riesigen Erdblock zu graben, die");
				Messenger.send(p, "gegnerischen Spieler auszuschalten");
				Messenger.send(p, "und als letzter zu überleben.\n ");
				sleep(4000);
				Messenger.send(p, "Während du gräbst kannst du Kisten finden.");
				Messenger.send(p, "Diese werden dir bei deiner Reise");
				Messenger.send(p, "durch die Erde sicher behilflich sein!\n ");
				sleep(4000);
				Messenger.send(p, "Das wäre es eigentlich auch schon und jetzt Viel");
				Messenger.send(p, "Glück und natürlich Vergnügen bei §lMoleCraft\n ");
				sleep(1000);
				p.getInventory().setItem(0, item);
				return;
			}
			catch (InterruptedException | NullPointerException e) {
				return;
			}
		}
	}
}
