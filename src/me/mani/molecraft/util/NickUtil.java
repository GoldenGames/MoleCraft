package me.mani.molecraft.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo.PlayerInfoData;
import net.minecraft.server.v1_8_R2.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R2.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

public class NickUtil {

	private static HashMap<Player, String> nickNames = new HashMap<>();
	
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private static Field modifiers = getField(Field.class, "modifiers");
	private static Field playerUuidField = getField(PacketPlayOutNamedEntitySpawn.class, "b");
	private static Field actionField = getField(PacketPlayOutPlayerInfo.class, "a");
	private static Field dataField = getField(PacketPlayOutPlayerInfo.class, "b");
	private static Field nameField = getField(GameProfile.class, "name");

	public static void nick(final Player p, final String name) {
		p.setDisplayName(name);
		nickNames.put(p, name);
		pool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					GameProfile prof = GameProfileBuilder.fetch(UUIDHandler.getUUID(ChatColor.stripColor(name)));
					nameField.set(prof, name);

					EntityPlayer entity = ((CraftPlayer) p).getHandle();

					PacketPlayOutEntityDestroy despawn = new PacketPlayOutEntityDestroy(entity.getId());

					PacketPlayOutPlayerInfo removeProfile = new PacketPlayOutPlayerInfo();
					setInfo(removeProfile, EnumPlayerInfoAction.REMOVE_PLAYER, removeProfile.new PlayerInfoData(entity.getProfile(), -1, null, null));

					PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo();
					setInfo(info, EnumPlayerInfoAction.ADD_PLAYER, info.new PlayerInfoData(prof, entity.ping, entity.playerInteractManager.getGameMode(), CraftChatMessage.fromString("§9" + name)[0]));

					PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(entity);
					playerUuidField.set(spawn, prof.getId());
					
					Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.getOnlinePlayers();

					synchronized (players) {
						for (Player player : players) {
							PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
							
							if (player != p)
								connection.sendPacket(despawn);
							connection.sendPacket(removeProfile);
						}
					}

					synchronized (this) {
						wait(50L);
					}

					synchronized (players) {
						for (Player player : players) {
							PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

							connection.sendPacket(info);
							if (player != p)
								connection.sendPacket(spawn);
						}
					}	
				} catch (Exception e) {
					e.printStackTrace();
					p.setDisplayName(p.getName());
					nickNames.remove(p);
				}
			}
		});
	}

	private static PacketPlayOutPlayerInfo setInfo(PacketPlayOutPlayerInfo packet, EnumPlayerInfoAction action, PlayerInfoData... data) {
		try {
			actionField.set(packet, action);
			dataField.set(packet, Arrays.asList(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packet;
	}

	private static Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);

			if (Modifier.isFinal(field.getModifiers())) {
				modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
			}

			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isNicked(Player player) {
		return nickNames.containsKey(player);
	}
	
	public static String getNick(Player player) {
		return nickNames.get(player);
	}
	
	public static Collection<String> getNicks() {
		return nickNames.values();
	}

}
