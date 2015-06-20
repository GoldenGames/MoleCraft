package me.mani.molecraft.util;

import org.bukkit.entity.Player;

public class RankHandler {

	public static String getPrefix(Player p) {
		if (p.hasPermission("admin"))
			return "§4";
		else if (p.hasPermission("dev"))
			return "§3";
		else if (p.hasPermission("mod"))
			return "§a";
		else if (p.hasPermission("architekt"))
			return "§b";
		else if (p.hasPermission("youtuber"))
			return "§5";
		else if (p.hasPermission("premium"))
			return "§6";
		else
			return "§9";
	}

	public static String getTabColor(Player p) {
		if (p.hasPermission("admin"))
			return "§4Admin §7| §4";
		else if (p.hasPermission("dev"))
			return "§3Dev §7| §b";
		else if (p.hasPermission("mod"))
			return "§aMod §7| §a";
		else if (p.hasPermission("architekt"))
			return "§b";
		else if (p.hasPermission("youtuber"))
			return "§5";
		else if (p.hasPermission("premium"))
			return "§6";
		else
			return "§9";
	}

	public static String getTeam(Player p) {
		if (p.hasPermission("admin"))
			return "Admin";
		else if (p.hasPermission("dev"))
			return "Developer";
		else if (p.hasPermission("mod"))
			return "Moderator";
		else if (p.hasPermission("sup"))
			return "Supporter";
		else if (p.hasPermission("architekt"))
			return "Architekt";
		else if (p.hasPermission("youtuber"))
			return "Youtuber";
		else if (p.hasPermission("premium"))
			return "Premium";
		else
			return "Spieler";
	}

	public static boolean isPremium(Player p) {
		return p.hasPermission("premium");
	}

}
