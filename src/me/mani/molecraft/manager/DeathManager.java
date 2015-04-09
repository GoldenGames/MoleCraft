package me.mani.molecraft.manager;

import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.listener.StatsEvent;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.listener.StatsListener;

import org.bukkit.entity.Player;

public class DeathManager {

	private static GameManager gameManager = MoleCraft.getInstance().gameManager;
	
	public static void handlePlayerDeath(Player deadPlayer) {
		MoleCraftPlayer moleCraftPlayer = MoleCraftPlayer.getMoleCraftPlayer(deadPlayer);
		boolean isOnline = deadPlayer.isOnline();
		
		if (isOnline) {
			deadPlayer.getInventory().clear();
		
			
			moleCraftPlayer.setDeathLocation(deadPlayer.getLocation());
			
			if (moleCraftPlayer.isIngame())
				moleCraftPlayer.setIngame(false);
		}	
		
		// Change the deaths and games playerstat by 1		
		StatsListener.onStatsChange(gameManager, new StatsEvent(deadPlayer, StatsEventType.DEATH, 1));
		
		if (gameManager.getPlayerLeft().size() < 1) {
			Messenger.sendAll("§7Der Spieler §e" + deadPlayer.getName() + " §7hat sich selbst begraben.");
			Messenger.sendAll("§7Niemand hat gewonnen.");
			gameManager.finishGame();
			return;
		}
		
		// Checkes if the player had been killed	
		if (deadPlayer.getKiller() == null) {
			Messenger.sendAll("§7Der Spieler §e" + deadPlayer.getName() + " §7hat sich selbst begraben.");
			Messenger.sendAll((
				(gameManager.getPlayerLeft().size() <= 1)
				? "§7Der Spieler §c" + gameManager.getPlayerLeft().get(0).getName() + " §7gewinnt das Spiel."
				: "§7Es leben noch §e" + gameManager.getPlayerLeft().size() + " Spieler.")
			);
			return;
		}
		
		Player killer = deadPlayer.getKiller();
		
		Messenger.sendAll("§7Der Spieler §e" + deadPlayer.getName() + " §7wurde von §e" + killer.getName() + " §7begraben.");
		Messenger.sendAll((
			(gameManager.getPlayerLeft().size() <= 1) 
			? "§7Der Spieler §c" + killer.getName() + " §7gewinnt das Spiel."
			: "§7Es leben noch §e" + gameManager.getPlayerLeft().size() + " Spieler.")
		);
		
		// Change the kills playerstat by 1	
		StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.KILL, 1));
		
		// Checks if there is a winner		
		if (gameManager.getPlayerLeft().size() > 1)
			return;
		
		// Change the wins playerstat by 1		
		StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.WIN, 1));
		
		// Let the game finish 		
		gameManager.finishGame();
	}
	
}
