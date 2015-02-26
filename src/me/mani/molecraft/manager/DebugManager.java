package me.mani.molecraft.manager;

import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.selection.LocationSetCommand;
import me.mani.molecraft.selection.SelectionListener;
import me.mani.molecraft.selection.SelectionSaveCommand;
import me.mani.molecraft.selection.SelectionTool;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DebugManager implements MainManager {
	
	private static final String PREFIX = "§7[§bMoleCraft§7] ";

	private static SelectionTool tool;

	private MoleCraft moleCraft;
	
	public DebugManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
		tool = new SelectionTool();
		new SelectionListener(tool);
		new SelectionSaveCommand();
		new LocationSetCommand();
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

	@Override
	public MoleCraft getMoleCraft() {
		return moleCraft;
	}
}
