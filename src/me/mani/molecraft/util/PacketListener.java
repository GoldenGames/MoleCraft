package me.mani.molecraft.util;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R2.PlayerConnection;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketListener {
	
	public static void listen(Player player, Consumer<PacketPlayInResourcePackStatus> consumer) {
		try {
			PlayerConnection oldConnection = ((CraftPlayer) player).getHandle().playerConnection;
			Field field = PlayerConnection.class.getDeclaredField("minecraftServer");
			field.setAccessible(true);
			MinecraftServer minecraftServer = (MinecraftServer) field.get(oldConnection);
			NetworkManager networkmanager = oldConnection.networkManager;
			EntityPlayer entityplayer = oldConnection.player;
			((CraftPlayer) player).getHandle().playerConnection = new PlayerConnectionInjector(minecraftServer, networkmanager, entityplayer, consumer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class PlayerConnectionInjector extends PlayerConnection {

		private Consumer<PacketPlayInResourcePackStatus> consumer;
		
		public PlayerConnectionInjector(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer, Consumer<PacketPlayInResourcePackStatus> consumer) {
			super(minecraftserver, networkmanager, entityplayer);
			this.consumer = consumer;
		}
		
		@Override
		public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus) {
			consumer.accept(packetplayinresourcepackstatus);
		}
		
	}

}
