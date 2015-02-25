package me.mani.molecraft.manager;

import java.util.HashMap;

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
	
	public boolean hasTeam(Player p) {
		return allPlayerTeams.get(p) != null;
	}
	
	public enum Team {
		CYAN (0, "§8Team Drachenfruchtgrau", DyeColor.GRAY),
		BLUE (1, "§3Team Blaubeerenblau", DyeColor.LIGHT_BLUE),
		GREEN (2, "§aTeam Limettengrün", DyeColor.LIME),
		YELLOW (3, "§eTeam Mandarinengelb", DyeColor.YELLOW),
		ORANGE (4, "§6Team Orangenorange", DyeColor.ORANGE),
		RED (5, "§4Team Apfelrot", DyeColor.RED),
		PURPLE (6, "§5Team Traubenpurpur", DyeColor.PURPLE),
		WHITE (7, "§fTeam Litschiweiß", DyeColor.WHITE);
		
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
