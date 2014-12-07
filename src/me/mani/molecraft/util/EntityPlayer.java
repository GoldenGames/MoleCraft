package me.mani.molecraft.util;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

public class EntityPlayer {
	
	private OfflinePlayer offPlayer;
	private Location loc;
	
	private int entityId;
	private PacketPlayOutNamedEntitySpawn fakePlayer;
	
	public EntityPlayer(OfflinePlayer p, Location loc) {
		this.offPlayer = p;
		this.loc = loc;
		
		this.entityId = ((CraftWorld) loc.getWorld()).getHandle().entityList.size();
		fakePlayer = new PacketPlayOutNamedEntitySpawn();
		DataWatcher data = new DataWatcher(null);
		data.a(6, (float) 20);
		
		PacketUtil.setField(fakePlayer, "a", entityId);
		PacketUtil.setField(fakePlayer, "b", new GameProfile(p.getUniqueId(), p.getName()));
		PacketUtil.setField(fakePlayer, "c", PacketUtil.toFixedPoint(loc.getX()));
		PacketUtil.setField(fakePlayer, "d", PacketUtil.toFixedPoint(loc.getY()));
		PacketUtil.setField(fakePlayer, "e", PacketUtil.toFixedPoint(loc.getZ()));
		PacketUtil.setField(fakePlayer, "f", PacketUtil.toPackedByte(loc.getPitch()));
		PacketUtil.setField(fakePlayer, "g", PacketUtil.toPackedByte(loc.getYaw()));
		PacketUtil.setField(fakePlayer, "h", 0);
		PacketUtil.setField(fakePlayer, "i", data);
	}	
	
	public OfflinePlayer getOfflinePlayer() {
		return this.offPlayer;
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public int getEntityId() {
		return entityId;
	}
	
	public void spawn() {
		PacketUtil.sendPacket(fakePlayer, Bukkit.getOnlinePlayers());
	}
	
	public void teleport(Location loc) {
		PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport();
		
		PacketUtil.setField(teleport, "a", entityId);
		PacketUtil.setField(teleport, "b", PacketUtil.toFixedPoint(loc.getX()));
		PacketUtil.setField(teleport, "c", PacketUtil.toFixedPoint(loc.getY()));
		PacketUtil.setField(teleport, "d", PacketUtil.toFixedPoint(loc.getZ()));
		PacketUtil.setField(teleport, "e", PacketUtil.toPackedByte(loc.getPitch()));
		PacketUtil.setField(teleport, "f", PacketUtil.toPackedByte(loc.getYaw()));
		
		PacketUtil.sendPacket(teleport, Bukkit.getOnlinePlayers());
	}
	
	public void remove() {
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entityId);
		
		PacketUtil.sendPacket(destroy, Bukkit.getOnlinePlayers());
	}
}
