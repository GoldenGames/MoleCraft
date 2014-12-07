package me.mani.molecraft.game;

import me.mani.molecraft.Message;
import me.mani.molecraft.Message.MessageType;
import me.mani.molecraft.StatsManager;
import me.mani.molecraft.util.AdvCommandExecuter;
import me.mani.molecraft.util.PlayerStats;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand extends AdvCommandExecuter {

	public StatsCommand() {
		super("stats");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		PlayerStats stats = StatsManager.getPlayerStats(p);
		Message.send(p, MessageType.SYNTAX, "§eDeine Stats:",
				"Kills: §e" + stats.getKills(),
				"Deaths: §e" + stats.getDeaths(),
				"Wins: §e" + stats.getWins(),
				"Games: §e" + stats.getGames(),
				"Chests: §e" + stats.getChests());
		
		return true;
	}

}
