package me.mani.molecraft.debug;

import me.mani.molecraft.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DebugManager extends Manager {

	private static SelectionTool tool;
	
	private static final String PREFIX = "§7[§bMoleCraft§7] ";
	
	public DebugManager() {
		tool = new SelectionTool();
		Bukkit.getPluginManager().registerEvents(new SelectionListener(tool), getPlugin());
		getPlugin().getCommand("save").setExecutor(new SelectionSaveCommand());
		getPlugin().getCommand("setpoint").setExecutor(new LocationSetCommand());
	}
	
	public static SelectionTool getSelectionTool() {
		return tool;
	}
	
	public static void send(Player p, MessageType type, String message) {
		p.sendMessage(PREFIX + type.getChatColor() + message);
	}
	
	public static void send(Player p, MessageType type, String... messages) {
		for (String s : messages)
			send(p, type, s);
	}
	
	public enum MessageType {
		INFO (ChatColor.YELLOW),
		ERROR (ChatColor.RED),
		TIP (ChatColor.GRAY),
		SYNTAX (ChatColor.GREEN);
		
		private ChatColor color;
		
		private MessageType(ChatColor color) {
			this.color = color;
		}
		
		public ChatColor getChatColor() {
			return color;
		}
	}
}
