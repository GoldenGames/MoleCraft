package me.mani.molecraft.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NametagUtil {
	
	private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	
	public static void changeNametag(Player player, String prefix) {
		Team team = scoreboard.getTeam(prefix);
		if (team == null) {
			team = scoreboard.registerNewTeam(prefix);
			team.setPrefix(prefix);
		}
		final Team finalTeam = team;
		UUIDHandler.getUUID(player.getDisplayName(), (uuid) -> finalTeam.addPlayer(Bukkit.getOfflinePlayer(uuid)));
		Bukkit.getOnlinePlayers().forEach((onlinePlayer) -> onlinePlayer.setScoreboard(scoreboard));
	}
	
}
