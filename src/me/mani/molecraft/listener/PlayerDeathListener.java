package me.mani.molecraft.listener;

import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.manager.GameManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends MoleCraftListener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent ev) {
		Player p = ev.getEntity();
		ev.setDroppedExp(0);
		ev.setKeepInventory(true);
		ev.setNewTotalExp(0);
		p.getInventory().clear();
		
		if (GameManager.isIngame(p))
			GameManager.removeIngamePlayer(p);
		
		if (GameManager.getPlayerLeft().size() < 1)
			GameManager.finishGame();
		
		ev.setDeathMessage(
			Messenger.getPrefix() + "§7Der Spieler §e" + p.getName() + " §7hat sich selbst begraben." + "\n" +
			Messenger.getPrefix() + (
				(GameManager.getPlayerLeft().size() <= 1)
				? "§7Der Spieler §c" + GameManager.getPlayerLeft().get(0).getName() + " §7gewinnt das Spiel."
				: "§7Es leben noch §e" + GameManager.getPlayerLeft().size() + " Spieler.")
		);
		
		// Change the deaths and games playerstat by 1		
		StatsListener.onStatsChange(gameManager, new StatsEvent(p, StatsEventType.DEATH, 1));
		StatsListener.onStatsChange(gameManager, new StatsEvent(p, StatsEventType.GAME, 1));
		
		// Checkes if the player had been killed	
		if (p.getKiller() == null)
			return;
		
		Player killer = p.getKiller();
		
		ev.setDeathMessage(
			Messenger.getPrefix() + "§7Der Spieler §e" + p.getName() + " §7wurde von §e" + killer.getName() + " §7begraben." + "\n" +
			Messenger.getPrefix() + (
				(GameManager.getPlayerLeft().size() <= 1) 
				? "§7Der Spieler §c" + killer.getName() + " §7gewinnt das Spiel."
				: "§7Es leben noch §e" + GameManager.getPlayerLeft().size() + " Spieler.")
		);
		
		// Change the kills playerstat by 1	
		StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.KILL, 1));
		
		// Checks if there is a winner		
		if (GameManager.getPlayerLeft().size() > 1)
			return;
		
		// Change the wins playerstat by 1		
		StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.WIN, 1));
		
		// Let the game finish 
		// TODO: Put this somewhere else	
		Block b = killer.getLocation().subtract(0, 1, 0).getBlock();
		b.setType(Material.JUKEBOX);
		Jukebox box = (Jukebox) b.getState();
		box.setPlaying(Material.RECORD_7);
		box.update();
		
		GameManager.finishGame();
	}

}
