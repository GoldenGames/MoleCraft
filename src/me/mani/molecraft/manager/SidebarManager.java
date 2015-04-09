package me.mani.molecraft.manager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import me.mani.molecraft.ArenaMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SidebarManager {
	
	private static final String FULL_BLOCK = "█";
	
	private ScoreboardManager scoreboardManager;
	private Map<Player, Scoreboard> scoreboards;
	private Map<Player, Objective> objectives;
	private int minZ, minY, maxZ, maxY, mapWidth, mapHeight;
	private boolean[][] isFilled;
	private Map<Player, Point> currentPlayerPositions;
	
	public SidebarManager() {
		scoreboardManager = Bukkit.getScoreboardManager();
		scoreboards = new HashMap<>();
		objectives = new HashMap<>();
	}
	
	// scoreboard.registerNewObjective(" §eMoleCraft ", "dummy");
	// objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	
	public void setupMap(ArenaMap arenaMap) {
		minZ = Math.min(arenaMap.getCornerLocation(0).getBlockZ(), arenaMap.getCornerLocation(1).getBlockZ()) + 2;
		minY = Math.min(arenaMap.getCornerLocation(0).getBlockY(), arenaMap.getCornerLocation(1).getBlockY()) + 2;
		maxZ = Math.max(arenaMap.getCornerLocation(0).getBlockZ(), arenaMap.getCornerLocation(1).getBlockZ()) - 2;
		maxY = Math.max(arenaMap.getCornerLocation(0).getBlockY(), arenaMap.getCornerLocation(1).getBlockY()) - 2;
		currentPlayerPositions = new HashMap<>();
		mapWidth = StringUtils.countMatches(arenaMap.getDisplayLore().get(0), FULL_BLOCK);
		mapHeight = arenaMap.getDisplayLore().size();
		isFilled = new boolean[mapWidth][mapHeight];
		int y = 0;
		for (String s : arenaMap.getDisplayLore()) {
			char lastColorCode = ' ';
			int x = 0;
			for (int i = 0; i < s.length(); i++) {
				if (lastColorCode != ' ' && s.charAt(i) == FULL_BLOCK.charAt(0))
					isFilled[x++][y] = lastColorCode == 'e';
				else if (s.charAt(i) == 'e' || s.charAt(i) == '8')
					lastColorCode = s.charAt(i);
			}
			y++;
		}
	}
	
	public void setupIngameSidebar(ArenaMap arenaMap) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
			Objective objective = scoreboard.registerNewObjective(" §eMoleCraft ", "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			scoreboards.put(player, scoreboard);
			objectives.put(player, objective);
//			int i = 9;
//			objective.getScore("§" + i).setScore(i--);
//			for (String s : arenaMap.getDisplayLore())
//				objective.getScore("§" + i + "  " + s).setScore(i--);
//			objective.getScore("§" + i).setScore(i--);
//			objective.getScore("§b " + arenaMap.getDisplayName()).setScore(i--);
		}
	}
	
	public void updateIngameSidebar() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			currentPlayerPositions.clear();
			currentPlayerPositions.put(player, new Point(Math.round((maxZ - player.getLocation().getBlockZ()) / 18) + 1, Math.round((maxY - player.getLocation().getBlockY()) / 18) + 1));
			for (String entry : scoreboards.get(player).getEntries())
				scoreboards.get(player).resetScores(entry);
			Objective objective = objectives.get(player);
			int i = 9;
			for (int y = 0; y < mapHeight; y++) {
				char lastColorCode = ' ';
				String line = "§" + i + "  ";
				for (int x = 0; x < mapWidth; x++) {
					if ((isFilled[x][y] && lastColorCode == 'e') || (!isFilled[x][y] && lastColorCode == '8'))
						line = line.concat(FULL_BLOCK);
					else
						if (x == currentPlayerPositions.get(player).x && y == currentPlayerPositions.get(player).y)
							line = line.concat("§a" + FULL_BLOCK);
						else
							line = line.concat((isFilled[x][y] ? "§e" : "§8") + FULL_BLOCK);
					lastColorCode = isFilled[x][y] ? 'e' : '8';
				}
				objective.getScore(line).setScore(i--);
			}
		}
	}
	
	public Scoreboard getScoreboard(Player player) {
		return scoreboards.get(player);
	}

}
