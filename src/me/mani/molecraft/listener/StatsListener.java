package me.mani.molecraft.listener;

import org.bukkit.Bukkit;

import me.mani.molecraft.Messenger;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.util.PlayerStats;

public class StatsListener {
	
	public static void onStatsChange(GameManager gameManager, StatsEvent ev) {
		PlayerStats stats = gameManager.statsManager.getPlayerStats(ev.getUUID());
		if (stats == null)
			return;
		switch (ev.getType()) {
		case KILL:
			stats.setKills(stats.getKills() + ev.getValue());
			break;
		case DEATH:
			stats.setDeaths(stats.getDeaths() + ev.getValue());
			break;
		case WIN:
			stats.setWins(stats.getWins() + ev.getValue());
			break;
		case GAME:
			stats.setGames(stats.getGames() + ev.getValue());
			break;
		case CHEST:
			stats.setChests(stats.getChests() + ev.getValue());
			break;
		}
		if (Bukkit.getPlayer(ev.getUUID()) != null)
			Messenger.sendBar(Bukkit.getPlayer(ev.getUUID()), "§e+ " + ev.getValue() + " " + ev.getType().getDisplayName(), true);
	}

}
