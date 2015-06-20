package me.mani.molecraft.commands;

import java.util.UUID;

import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.util.PlayerStats;
import me.mani.molecraft.util.UUIDHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatsCommand extends MoleCraftCommand {

	public StatsCommand(MainManager mainManager) {
		super("stats", mainManager);
	}

	@Override
	public String onCommand(Player p, String[] args) {
	
		if (args.length == 0) {
			PlayerStats.getPlayerStats(p, (stats) -> {
				Messenger.send(p,
					"§eDeine Stats:\n" +
					"- §aRang: §e" + stats.getRanking() + "\n" +
					"- §aPunkte: §e" + stats.getPoints() + "\n" +
					"- §aKills: §e" + stats.getKills() + "\n" +
					"- §aTode: §e" + stats.getDeaths() + "\n" +
					"- §aK/D: §e" + (double) Math.round((double) stats.getKills() / ((double) stats.getDeaths() < 1 ? 1 : stats.getDeaths()) * 100) / 100 + "\n" +
					"- §aSiege: §e" + stats.getWins() + "\n" +
					"- §aSiegwahrscheinlichkeit: §e" + Math.round((double) stats.getWins() / ((double) stats.getGames() < 1 ? 1 : stats.getGames()) * 100) + "%" + "\n" +
					"- §aSpiele gespielt: §e" + stats.getGames() + "\n" +
					"- §aKisten geöffnet: §e" + stats.getChests()
				);
			});
			return null;
		}
		else if (args.length == 1) {
			String playerName = args[0];
			if (Bukkit.getPlayer(playerName) == null)
				UUIDHandler.getUUID(playerName, (uuid) -> sendStats(p, uuid));
			else
				sendStats(p, Bukkit.getPlayer(playerName).getUniqueId());
			return null;
		}
		return "§b/stats [SPIELER]";	
	}
	
	private void sendStats(Player player, UUID uuid) {
		if (uuid == null)
			Messenger.send(player, "§cFehler bei der Datenübertragung.");
		else if (!PlayerStats.hasPlayerStats(uuid))
			Messenger.send(player, "§cDieser Spieler hat noch keine Statistik.");
		else
			PlayerStats.getPlayerStats(uuid, (stats) -> {
				Messenger.send(player, 
					"§e" + stats.getLastName() + "'s Stats:\n" +
					"- §aRang: §e" + stats.getRanking() + "\n" +
					"- §aPunkte: §e" + stats.getPoints() + "\n" +
					"- §aKills: §e" + stats.getKills() + "\n" +
					"- §aTode: §e" + stats.getDeaths() + "\n" +
					"- §aK/D: §e" + (double) Math.round((double) stats.getKills() / ((double) stats.getDeaths() < 1 ? 1 : stats.getDeaths()) * 100) / 100 + "\n" +
					"- §aSiege: §e" + stats.getWins() + "\n" +
					"- §aSiegwahrscheinlichkeit: §e" + Math.round((double) stats.getWins() / ((double) stats.getGames() < 1 ? 1 : stats.getGames()) * 100) + "%" + "\n" +
					"- §aSpiele gespielt: §e" + stats.getGames() + "\n" +
					"- §aKisten geöffnet: §e" + stats.getChests()
						);
			});
	}
}
