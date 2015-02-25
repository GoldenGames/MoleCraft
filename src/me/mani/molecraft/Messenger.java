package me.mani.molecraft;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Messenger {
	
	private static String prefix = "";
	
	public static void sendAll(String message) {
		sendAll(message, false);
	}
	
	public static void sendAll(String message, boolean noPrefix) {
		if (message == null)
			return;
		Bukkit.broadcastMessage(noPrefix ? "" : prefix + message);
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
		player.sendMessage(noPrefix ? "" : prefix + message);
	}
	
	public static void sendBar(Player player, String message, boolean noPrefix) {
		if (message == null)
			return;
		PacketPlayOutChat messagePacket = new PacketPlayOutChat(ChatSerializer.a("{text:\"" + (noPrefix ? "" : prefix) + message + "\"}"), (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(messagePacket);
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static void setPrefix(String newPrefix) {
		prefix = newPrefix;
	}
	
}