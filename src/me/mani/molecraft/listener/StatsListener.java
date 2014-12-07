package me.mani.molecraft.listener;

import me.mani.molecraft.StatsManager;
import me.mani.molecraft.util.PlayerStats;

public class StatsListener {
	
	public static void onStatsChange(StatsEvent ev) {
		PlayerStats stats = StatsManager.getPlayerStats(ev.getUUID());
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
		stats.sendData();
	}

}
