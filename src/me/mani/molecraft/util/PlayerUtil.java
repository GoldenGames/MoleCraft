package me.mani.molecraft.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedEntitySpawn;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

public class PlayerUtil {
	
	// Method below doesn't actually works
	
	@Deprecated
	public static void changeNameTag(Player p, String name) {
		if (name.length() > 16)
			name = name.substring(0, 16);
		
		EntityHuman entityHuman = ((CraftPlayer) p).getHandle();
		
		PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(p.getEntityId());
		PacketPlayOutNamedEntitySpawn packetSpawn = new PacketPlayOutNamedEntitySpawn(entityHuman);
		
		PacketUtil.setField(packetSpawn, "b", new GameProfile(p.getUniqueId(), name));
		
		List<Player> allPlayer = new ArrayList<>();
		allPlayer.addAll(Arrays.asList(Bukkit.getOnlinePlayers()));
		if (p.isOnline())
			allPlayer.remove(p);
		PacketUtil.sendPacket(packetDestroy, allPlayer.toArray(new Player[]{}));
		PacketUtil.sendPacket(new PacketPlayOutNamedEntitySpawn(entityHuman), allPlayer.toArray(new Player[]{}));
	}

}
