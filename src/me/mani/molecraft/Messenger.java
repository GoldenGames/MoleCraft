package me.mani.molecraft;

import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Messenger {
	
	private static String prePrefix = "";
	private static String prefix = "";
	private static String suffix = "";
	
	public static void sendAll(String message) {
		sendAll(message, false);
	}
	
	public static void sendAll(String message, boolean noPrefix) {
		if (message == null)
			return;
		Bukkit.broadcastMessage(noPrefix ? "" : prefix + message.replaceAll("§r", prePrefix));
	}
	
	public static void sendBarAll(String message, boolean noPrefix) {
		if (message == null)
			return;
		for (Player player : Bukkit.getOnlinePlayers())
			sendBar(player, message, noPrefix);
	}
	
	public static void send(Player player, String message) {
		send (player, message, false);
	}
	
	public static void send(Player player, String message, boolean noPrefix) {
		if (message == null)
			return;
		player.sendMessage(noPrefix ? "" : prefix + message.replaceAll("§r", prePrefix));
	}
	
	public static void sendBar(Player player, String message, boolean noPrefix) {
		if (message == null)
			return;
		PacketPlayOutChat messagePacket = new PacketPlayOutChat(ChatSerializer.a("{text:\"" + (noPrefix ? "" : prefix) + message.replaceAll("§r", prePrefix) + "\"}"), (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(messagePacket);
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static void setPrefix(String prefix) {
		Messenger.prefix = prefix;
		prePrefix = prefix.substring(prefix.length() - 2, prefix.length());
	}
	
	public static String getSuffix() {
		return suffix;
	}
	
	public static void setSuffix(String suffix) {
		Messenger.suffix = suffix;
	}
	
}
