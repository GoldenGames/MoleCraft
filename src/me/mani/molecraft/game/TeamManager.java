package me.mani.molecraft.game;

import java.util.HashMap;

import me.mani.molecraft.Manager;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

public class TeamManager extends Manager {

	private static HashMap<Player, Team> allPlayerTeams = new HashMap<>();
	
	public static void setTeam(Player p, Team team) {
		allPlayerTeams.put(p, team);
	}
	
	public static Team getTeam(Player p) {
		return allPlayerTeams.get(p);
	}
	
	public static boolean hasTeam(Player p) {
		return allPlayerTeams.get(p) != null;
	}
	
	public static enum Team {
		CYAN (1, "§8Team Drachenfruchtgrau", DyeColor.GRAY),
		BLUE (2, "§3Team Blaubeerenblau", DyeColor.LIGHT_BLUE),
		GREEN (3, "§aTeam Limettengrün", DyeColor.LIME),
		YELLOW (4, "§eTeam Mandarinengelb", DyeColor.YELLOW),
		ORANGE (5, "§6Team Orangenorange", DyeColor.ORANGE),
		RED (6, "§4Team Apfelrot", DyeColor.RED),
		PURPLE (7, "§5Team Traubenpurpur", DyeColor.PURPLE),
		WHITE (8, "§fTeam Litschiweiß", DyeColor.WHITE);
		
		private int id;
		private String name;
		private DyeColor color;
		
		private Team(int id, String name, DyeColor color) {
			this.id = id;
			this.name = name;
			this.color = color;
		}
		
		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public DyeColor getDyeColor() {
			return color;
		}
		
		public static Team getById(int id) {
			for (Team t : values())
				if (t.getId() == id)
					return t;
			return null;
		}
	}
	
}
