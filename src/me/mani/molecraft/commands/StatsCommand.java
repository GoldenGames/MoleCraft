package me.mani.molecraft.commands;

import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.util.PlayerStats;

import org.bukkit.entity.Player;

public class StatsCommand extends MoleCraftCommand {

	public StatsCommand() {
		super("stats");
	}

	@Override
	public String onCommand(Player p, String[] args) {
		PlayerStats stats = gameManager.statsManager.getPlayerStats(p);
		return 
			"§eDeine Stats:\n" +
			"Kills: §e" + stats.getKills() + "\n" +
			"Deaths: §e" + stats.getDeaths() + "\n" +
			"Wins: §e" + stats.getWins() + "\n" +
			"Games: §e" + stats.getGames() + "\n" +
			"Chests: §e" + stats.getChests()
		;		
	}
}
