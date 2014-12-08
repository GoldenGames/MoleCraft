package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Message;
import me.mani.molecraft.Message.MessageType;
import me.mani.molecraft.game.LocationManager.SpecialLocation;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener extends AdvListener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		Player p = ev.getPlayer();
		
		// Item Interact in Lobby
		
		if (getState() != GameState.LOBBY)
			return;
		
		if (ev.getAction() == Action.RIGHT_CLICK_AIR || ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = ev.getItem();
			if (item == null || item.getType() == null)
				return;
				
			// Paper - Explanation
			
			if (item.getType() == Material.PAPER) {
				new TutorialThread(p, item).start();
				p.getInventory().removeItem(item);
			}
			
			// Firework - Teleport to Parkour
			
			if (item.getType() == Material.FIREWORK)
				p.teleport(SpecialLocation.PARKOUR_SPAWN);			
		}
		
		ev.setCancelled(true);	
	}

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
				Message.send(p, MessageType.INFO, "In MoleCraft geht es darum sich durch");
				Message.send(p, MessageType.NO_PREFIX.custom(ChatColor.YELLOW), "einen riesigen Erdblock zu graben, die");
				Message.send(p, MessageType.NO_PREFIX.custom(ChatColor.YELLOW), "gegnerischen Spieler auszuschalten");
				Message.send(p, MessageType.NO_PREFIX.custom(ChatColor.YELLOW), "und als letzter zu überleben.\n ");
				sleep(4000);
				Message.send(p, MessageType.INFO, "Während du gräbst kannst du Kisten finden.");
				Message.send(p, MessageType.NO_PREFIX.custom(ChatColor.YELLOW), "Diese werden dir bei deiner Reise");
				Message.send(p, MessageType.NO_PREFIX.custom(ChatColor.YELLOW), "durch die Erde sicher behilflich sein!\n ");
				sleep(4000);
				Message.send(p, MessageType.INFO, "Das wäre es eigentlich auch schon und jetzt Viel");
				Message.send(p, MessageType.NO_PREFIX.custom(ChatColor.YELLOW), "Glück und natürlich Vergnügen bei §lMoleCraft\n ");
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
