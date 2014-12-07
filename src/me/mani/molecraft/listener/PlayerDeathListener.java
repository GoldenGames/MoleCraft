package me.mani.molecraft.listener;

import me.mani.molecraft.Message;
import me.mani.molecraft.game.GameManager;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends AdvListener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent ev) {
		Player p = ev.getEntity();
		ev.setDroppedExp(0);
		ev.setKeepInventory(true);
		ev.setNewTotalExp(0);
		p.getInventory().clear();
		
		if (GameManager.isIngame(p))
			GameManager.removeIngamePlayer(p);
		
		ev.setDeathMessage(Message.PREFIX + "§7Der Spieler §e" + p.getName() + " §7hat sich selbst begraben.\n"
				+ Message.SPACE + (GameManager.getPlayerLeft().size() <= 1 ? 
					"§7Der Spieler §c" + GameManager.getPlayerLeft().get(0).getName() + " §7gewinnt das Spiel." :
					"§7Es leben noch §e" + GameManager.getPlayerLeft().size() + " Spieler."));
		
		// Stats - DEATH + GAME
		
		StatsListener.onStatsChange(new StatsEvent(p, StatsEventType.DEATH, 1));
		StatsListener.onStatsChange(new StatsEvent(p, StatsEventType.GAME, 1));
		
		// Ab hier wenn der Spieler von einem anderen Spieler "begraben" wurde
		
		if (p.getKiller() == null)
			return;
		
		Player killer = p.getKiller();
		
		ev.setDeathMessage(Message.PREFIX + "§7Der Spieler §e" + p.getName() + " §7wurde von §e" + killer.getName() + " §7begraben.\n"
				+ Message.SPACE + (GameManager.getPlayerLeft().size() <= 1 ? 
					"§7Der Spieler §c" + killer.getName() + " §7gewinnt das Spiel." :
					"§7Es leben noch §e" + GameManager.getPlayerLeft().size() + " Spieler."));
		
		// Stats - KILL
		
		StatsListener.onStatsChange(new StatsEvent(killer, StatsEventType.KILL, 1));
		
		// Ab hier wenn es einen Gewinner gibt
		
		if (GameManager.getPlayerLeft().size() > 1)
			return;
		
		// Stats - WIN 
		
		StatsListener.onStatsChange(new StatsEvent(killer, StatsEventType.WIN, 1));
		
		// Finish Game
		
		Block b = killer.getLocation().subtract(0, 1, 0).getBlock();
		b.setType(Material.JUKEBOX);
		Jukebox box = (Jukebox) b.getState();
		box.setPlaying(Material.RECORD_7);
		box.update();
		GameManager.finishGame();
	}

}
