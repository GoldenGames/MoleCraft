package me.mani.molecraft.manager;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

public class TeamManager {

	private HashMap<Player, Team> allPlayerTeams = new HashMap<>();
	
	public void setTeam(Player p, Team team) {
		allPlayerTeams.put(p, team);
	}
	
	public Team getTeam(Player p) {
		return allPlayerTeams.get(p);
	}
	
	public Player getPlayer(Team team) {
		for (Player player : allPlayerTeams.keySet())
			if (allPlayerTeams.get(player) == team)
				return player;
		return null;
	}
	
	public boolean hasTeam(Player p) {
		return allPlayerTeams.get(p) != null;
	}
	
	public enum Team {
		RED (5, "Team Apfelrot", ChatColor.DARK_RED, DyeColor.RED),
		ORANGE (4, "Team Orangenorange", ChatColor.GOLD, DyeColor.ORANGE),
		YELLOW (3, "Team Mandarinengelb", ChatColor.YELLOW, DyeColor.YELLOW),
		GREEN (2, "Team Limettengrün", ChatColor.GREEN, DyeColor.LIME),
		PURPLE (6, "Team Traubenpurpur", ChatColor.LIGHT_PURPLE, DyeColor.PURPLE),
		GRAY (0, "Team Drachenfruchtgrau", ChatColor.DARK_GRAY, DyeColor.GRAY),
		BLUE (1, "Team Blaubeerenblau", ChatColor.DARK_AQUA, DyeColor.LIGHT_BLUE),
		WHITE (7, "Team Litschiweiß", ChatColor.WHITE, DyeColor.WHITE);
		
		private int id;
		private String name;
		private ChatColor chatColor;
		private DyeColor dyeColor;
		
		private Team(int id, String name, ChatColor chatColor, DyeColor dyeColor) {
			this.id = id;
			this.name = name;
			this.chatColor = chatColor;
			this.dyeColor = dyeColor;
		}
		
		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDisplayName() {
			return chatColor + name;
		}
		
		public ChatColor getChatColor() {
			return chatColor;
		}
		
		public DyeColor getDyeColor() {
			return dyeColor;
		}
		
		public static Team getById(int id) {
			for (Team t : values())
				if (t.getId() == id)
					return t;
			return null;
		}
	}
	
}
