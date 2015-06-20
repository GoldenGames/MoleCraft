package me.mani.molecraft.manager;

import me.mani.molecraft.Effects;
import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.listener.StatsEvent;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.listener.StatsListener;
import me.mani.molecraft.util.StringUtils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DeathManager {

	private static GameManager gameManager = MoleCraft.getInstance().gameManager;
	
	public static void handlePlayerDeath(Player deadPlayer) {
		MoleCraftPlayer moleCraftPlayer = MoleCraftPlayer.getMoleCraftPlayer(deadPlayer);
		boolean isOnline = deadPlayer != null && deadPlayer.isOnline();
		
		if (isOnline) {
			deadPlayer.getInventory().clear();
		
			moleCraftPlayer.setDeathLocation(deadPlayer.getLocation());
			
			if (moleCraftPlayer.isIngame())
				moleCraftPlayer.setIngame(false);
			
			SpectatorManager.setSpectator(deadPlayer);
		}	
		
		// Change the deaths and games playerstat by 1		
		StatsListener.onStatsChange(gameManager, new StatsEvent(deadPlayer, StatsEventType.DEATH, 1));
		
		if (gameManager.getPlayerLeft().size() < 1 && GameState.getGameState() == GameState.SHUTDOWN) {
			Messenger.sendAll("Der Spieler " + Messenger.getSuffix() + deadPlayer.getDisplayName() + " §rhat sich selbst begraben.");
			Messenger.sendAll("Niemand hat gewonnen.");
		}
		
		// Checkes if the player had been killed	
		else if (deadPlayer.getKiller() == null) {
			Messenger.sendAll("Der Spieler " + Messenger.getSuffix() + deadPlayer.getDisplayName() + " §rhat sich selbst begraben.");
			Messenger.sendAll((
				(gameManager.getPlayerLeft().size() <= 1)
				? "Der Spieler " + gameManager.getPlayerLeft().get(0).getDisplayName() + " gewinnt das Spiel."
				: "Es leben noch " + Messenger.getSuffix() + gameManager.getPlayerLeft().size() + " §rSpieler.")
			);
		}
		else {		
			Player killer = deadPlayer.getKiller();
			Effects.play(killer, Sound.LEVEL_UP);
		
			Messenger.sendAll("Der Spieler " + Messenger.getSuffix() + deadPlayer.getDisplayName() + " §rwurde von " + Messenger.getSuffix() + killer.getDisplayName() + " §rbegraben.");
			Messenger.sendAll((
					(gameManager.getPlayerLeft().size() <= 1) 
					? "Der Spieler " + killer.getDisplayName() + " gewinnt das Spiel."
							: "Es leben noch " + Messenger.getSuffix() + gameManager.getPlayerLeft().size() + " §rSpieler.")
					);
		
			
		
			// Sends the health of the killer to the death player
			Messenger.send(deadPlayer, "Leben von " + Messenger.getSuffix() + killer.getDisplayName() + "§r: §c" + StringUtils.generateHealthString(killer.getHealth(), "§c", "§7"));
		
			// Change the kills playerstat by 1	
			StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.KILL, 1));
		
			// Change the wins playerstat by 1		
			StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.WIN, 1));
		}
		
		// Checks if there is a winner and if ends the game
		if (gameManager.getPlayerLeft().size() <= 1)
			gameManager.finishGame();
	}
	
}
