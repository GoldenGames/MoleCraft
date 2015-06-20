package me.mani.molecraft.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.mani.molecraft.manager.GameManager;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import net.minecraft.server.v1_8_R2.PlayerInteractManager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

public class CustomTablist {
	
	private static Map<Integer, String> lines = new HashMap<>();
	
	public static void sendCustomTablist(Player player) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		PlayerConnection connection = entityPlayer.playerConnection;
		MinecraftServer minecraftServer = entityPlayer.server;
		
		PacketPlayOutPlayerInfo playerInfoPacket;
		
//		// Remove other entries and remove own entry to others
//		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//			((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(playerInfoPacket);
//			PacketPlayOutPlayerInfo onlinePlayerInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) onlinePlayer).getHandle());
//			connection.sendPacket(onlinePlayerInfoPacket);
//		}
		
		Map<Integer, String> lines = configureLines(player);
		
		// Add new entries
		for (int i = 10; i <= 90; i++) {
			if (i > 30 && i < 50 && Bukkit.getOnlinePlayers().size() > i - 30) {
				EntityPlayer onlineEntityPlayer = ((CraftPlayer) Bukkit.getOnlinePlayers().toArray()[i - 31]).getHandle();
				onlineEntityPlayer.listName = CraftChatMessage.fromString("§" + (i / 10) + "§" + (i - 10 * (i / 10)) + onlineEntityPlayer.displayName)[0];
				playerInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, onlineEntityPlayer);
			}
			else {
				String lineText = "§" + (i / 10) + "§" + (i - 10 * (i / 10)) + lines.get(i - 10);
				GameProfile gameProfile = new GameProfile(new UUID(i, i), lineText.length() > 16 ? lineText.substring(0, 16) : lineText);
				EntityPlayer fakeEntityPlayer = new EntityPlayer(minecraftServer, entityPlayer.u(), gameProfile, new PlayerInteractManager(entityPlayer.world));
				fakeEntityPlayer.listName = CraftChatMessage.fromString(lineText)[0];
				fakeEntityPlayer.ping = 1000;
				playerInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, fakeEntityPlayer);
			}
			connection.sendPacket(playerInfoPacket);
		}
		
	}
	
	public static void updateCustomTablist(Player player) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		PlayerConnection connection = entityPlayer.playerConnection;
		MinecraftServer minecraftServer = entityPlayer.server;
		
		PacketPlayOutPlayerInfo playerInfoPacket;
		Map<Integer, String> lines = configureLines(player);
		
		// Override entries
		for (int i = 10; i <= 90; i++) {
			if (i > 30 && i < 50 && Bukkit.getOnlinePlayers().size() > i - 30) {
				EntityPlayer onlineEntityPlayer = ((CraftPlayer) Bukkit.getOnlinePlayers().toArray()[i - 31]).getHandle();
				onlineEntityPlayer.listName = CraftChatMessage.fromString("§" + (i / 10) + "§" + (i - 10 * (i / 10)) + onlineEntityPlayer.displayName)[0];
				playerInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, onlineEntityPlayer);
			}
			else {
				String lineText = "§" + (i / 10) + "§" + (i - 10 * (i / 10)) + lines.get(i - 10);
				GameProfile gameProfile = new GameProfile(new UUID(i, i), lineText.length() > 16 ? lineText.substring(0, 16) : lineText);
				EntityPlayer fakeEntityPlayer = new EntityPlayer(minecraftServer, entityPlayer.u(), gameProfile, new PlayerInteractManager(entityPlayer.world));
				fakeEntityPlayer.listName = CraftChatMessage.fromString(lineText)[0];
				fakeEntityPlayer.ping = 1000;
				playerInfoPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, fakeEntityPlayer);
			}
			connection.sendPacket(playerInfoPacket);
		}
	}
	
	private static Map<Integer, String> configureLines(Player player) {
		Map<Integer, String> playerLines = new HashMap<>(lines);
		int i = 21;
		for (Player onlinePlayer : Bukkit.getOnlinePlayers())
			playerLines.put(i++, onlinePlayer.getDisplayName());
		PlayerStats.getPlayerStats(player, (stats) -> {
			playerLines.put(61, "§7Rang: §e" + stats.getRanking());
			playerLines.put(62, "§7Punkte: §e" + stats.getPoints());
			playerLines.put(63, "§7Kills: §e" + stats.getKills());
			playerLines.put(64, "§7Tode: §e" + stats.getDeaths());
			playerLines.put(65, "§7Siege: §e" + stats.getWins());
			playerLines.put(66, "§7Spiele: §e" + stats.getGames());
			playerLines.put(67, "§7Kisten: §e" + stats.getChests());
		});
		return playerLines;
	}
	
	static {
		for (int i = 0; i <= 80; i++)
			lines.put(i, " ");
		lines.put(0, " §e» Freunde");
		lines.put(1, "§7Mit /friend kannst");
		lines.put(2, "§7du Freunde finden");
		lines.put(3, "§7und verwalten.");
		lines.put(20, " §a» Runde");
		lines.put(40, " §e» Party");
		lines.put(41, "§7Mit /party kannst");
		lines.put(42, "§7du deine Party");
		lines.put(43, "§7verwalten.");
		lines.put(60, " §e» Stats");
		lines.put(78, "§7GameId: §e" + GameManager.getGameId());
		lines.put(79, "§8MoleCraft ^mani");
	}
	
}
