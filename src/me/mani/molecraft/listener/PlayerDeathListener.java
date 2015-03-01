package me.mani.molecraft.listener;

import java.util.Iterator;

import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.util.BlockMath;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerDeathListener extends MoleCraftListener {
	
	public PlayerDeathListener(MainManager mainManager) {
		super(mainManager);
	}

	private BukkitTask blockBreakTask;
	
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
//		StatsListener.onStatsChange(gameManager, new StatsEvent(p, StatsEventType.DEATH, 1));
//		StatsListener.onStatsChange(gameManager, new StatsEvent(p, StatsEventType.GAME, 1));
		
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
//		StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.KILL, 1));
		
		// Checks if there is a winner		
		if (GameManager.getPlayerLeft().size() > 1)
			return;
		
		// Change the wins playerstat by 1		
//		StatsListener.onStatsChange(gameManager, new StatsEvent(killer, StatsEventType.WIN, 1));
		
		// Let the game finish 
		// TODO: Put this somewhere else	
		ArenaMap arenaMap = ArenaMap.getCurrentMap();
		Iterator<Block> dirtBlockIterator = BlockMath.getBlocks(arenaMap.getCornerLocation(0), arenaMap.getCornerLocation(1), Material.DIRT).iterator();
		blockBreakTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++)
				if (dirtBlockIterator.hasNext())
					dirtBlockIterator.next().breakNaturally();
				else  {
					blockBreakTask.cancel();
					break;
				}
			}
			
		}.runTaskTimer(gameManager.getMoleCraft(), 20L, 1L);
		
		GameManager.finishGame();
	}

}
