package me.mani.molecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Message {

	public static final String PREFIX = "§7[§bMoleCraft§7] ";
	public static final String SPACE = "               ";
	
	public static void send(Player p, MessageType type, String message) {
		if (type == MessageType.NO_PREFIX)
			p.sendMessage(SPACE + type.getChatColor() + message);
		else
			p.sendMessage(PREFIX + type.getChatColor() + message);
	}
	
	public static void send(Player p, MessageType type, String... messages) {
		for (String s : messages)
			send(p, type, s);
	}
	
	public static void sendAll(MessageType type, String message) {
		for (Player p : Bukkit.getOnlinePlayers())
			send(p, type, message);
	}
	
	public static void sendAll(MessageType type, String... messages) {
		for (String s : messages)
			sendAll(type, s);
	}
	
	public static void play(Player p, Sound sound) {
		p.playSound(p.getLocation(), sound, 1, 1);
	}
	
	public static void playAll(Sound sound) {
		for (Player p : Bukkit.getOnlinePlayers())
			p.playSound(p.getLocation(), sound, 1, 1);
	}
	
	public enum MessageType {
		INFO (ChatColor.YELLOW),
		ERROR (ChatColor.RED),
		TIP (ChatColor.GRAY),
		SYNTAX (ChatColor.GREEN),
		CUSTOM (ChatColor.DARK_GRAY),
		NO_PREFIX (ChatColor.DARK_GRAY);
		
		private ChatColor color;
		
		private MessageType(ChatColor color) {
			this.color = color;
		}
				
		public MessageType custom(ChatColor c) {
			color = c;
			return this;
		}
		
		public ChatColor getChatColor() {
			return color;
		}
	}	
}
