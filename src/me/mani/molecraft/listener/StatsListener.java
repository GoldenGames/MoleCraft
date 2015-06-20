package me.mani.molecraft.listener;

import me.mani.goldenapi.util.Title;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.util.PlayerStats;


import org.bukkit.Bukkit;

public class StatsListener {
	
	public static void onStatsChange(GameManager gameManager, StatsEvent ev) {
		PlayerStats.getPlayerStats(ev.getUUID(), (stats) -> {
			if (stats == null)
				return;
			switch (ev.getType()) {
			case KILL:
				stats.setKills(stats.getKills() + ev.getValue());
				if (stats.hasOfflinePlayerStats())
					stats.getOfflinePlayerStats().setKills(stats.getOfflinePlayerStats().getKills() + ev.getValue());
				break;
			case DEATH:
				stats.setDeaths(stats.getDeaths() + ev.getValue());
				if (stats.hasOfflinePlayerStats())
					stats.getOfflinePlayerStats().setDeaths(stats.getOfflinePlayerStats().getDeaths() + ev.getValue());
				break;
			case WIN:
				stats.setWins(stats.getWins() + ev.getValue());
				if (stats.hasOfflinePlayerStats())
					stats.getOfflinePlayerStats().setWins(stats.getOfflinePlayerStats().getWins() + ev.getValue());
				break;
			case GAME:
				stats.setGames(stats.getGames() + ev.getValue());
				if (stats.hasOfflinePlayerStats())
					stats.getOfflinePlayerStats().setGames(stats.getOfflinePlayerStats().getGames() + ev.getValue());
				break;
			case CHEST:
				stats.setChests(stats.getChests() + ev.getValue());
				if (stats.hasOfflinePlayerStats())
					stats.getOfflinePlayerStats().setChests(stats.getOfflinePlayerStats().getChests() + ev.getValue());
				break;
			}
			stats.setPoints(stats.getPoints() + ev.getType().getPoints());
			if (stats.hasOfflinePlayerStats())
				stats.getOfflinePlayerStats().setPoints(stats.getOfflinePlayerStats().getPoints() + ev.getValue());
			if (Bukkit.getPlayer(ev.getUUID()) != null)
				Messenger.sendBar(Bukkit.getPlayer(ev.getUUID()), Messenger.getSuffix() + '+' + ev.getValue() + " §r" + ev.getType().getDisplayName(), true);
				if (ev.getType().getPoints() != 0)
					new Title(
						ev.getType() == StatsEventType.WIN ? "§4Gewonnen!" : "", 
						Messenger.getSuffix() + "+" + ev.getType().getPoints() + " Punkte"
					).send(Bukkit.getPlayer(ev.getUUID()));
		});
	}

}
