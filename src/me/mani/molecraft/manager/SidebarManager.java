package me.mani.molecraft.manager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.manager.TeamManager.Team;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SidebarManager {
	
	private static final String FULL_BLOCK = "█";
	private static final String SHIZZLE_BLOCK = "▓";
	
	private ScoreboardManager scoreboardManager;
	private Map<Player, Scoreboard> scoreboards;
	private Map<Player, Objective> objectives;
	private int minZ, minY, maxZ, maxY, mapWidth, mapHeight;
	private boolean[][] isFilled;
	private Map<Player, Point> currentPlayerPositions;
	private String displayTitle;
	
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
			displayTitle = " §eMoleCraft ";
			Objective objective = scoreboard.registerNewObjective(displayTitle, "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			Team team = MoleCraft.getInstance().gameManager.teamManager.getTeam(player);
			player.setPlayerListName(team.getChatColor() + player.getDisplayName());
			player.setScoreboard(scoreboard);
			scoreboards.put(player, scoreboard);
			objectives.put(player, objective);
		}
	}
	
	public void updateIngameSidebar() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			currentPlayerPositions.clear();
			currentPlayerPositions.put(player, new Point(mapWidth - Math.round((maxZ - player.getLocation().getBlockZ()) / 18) - 2, Math.round((maxY - player.getLocation().getBlockY()) / 18) + 1));
			for (String entry : scoreboards.get(player).getEntries())
				scoreboards.get(player).resetScores(entry);
			Objective objective = objectives.get(player);
			objective.setDisplayName(displayTitle);
			
			int i = 9;
			objective.getScore("§" + i).setScore(i--);
			for (int y = 0; y < mapHeight; y++) {
				char lastColorCode = ' ';
				String line = "§" + i + "  ";
				for (int x = 0; x < mapWidth; x++) {
					if (x == currentPlayerPositions.get(player).x && y == currentPlayerPositions.get(player).y) {
						line = line.concat("§a" + (isFilled[x][y] ? FULL_BLOCK : SHIZZLE_BLOCK));
						lastColorCode = 'a';
					}
					else if ((isFilled[x][y] && lastColorCode == 'e') || (!isFilled[x][y] && lastColorCode == '8'))
						line = line.concat(isFilled[x][y] ? FULL_BLOCK : SHIZZLE_BLOCK);
					else {
						line = line.concat(isFilled[x][y] ? ("§e" + FULL_BLOCK) : ("§8" + SHIZZLE_BLOCK));
						lastColorCode = isFilled[x][y] ? 'e' : '8';
					}
				}
				objective.getScore(line).setScore(i--);
			}
			objective.getScore("§" + i).setScore(i--);
			objective.getScore(" §b" + ArenaMap.getCurrentMap().getDisplayName()).setScore(i--);
		}
	}
	
	public Scoreboard getScoreboard(Player player) {
		return scoreboards.get(player);
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}
	
}
