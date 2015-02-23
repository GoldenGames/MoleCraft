package me.mani.molecraft.util;

import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleEffect {
	
	public static class Offset {
		
		private float x;
		private float y;
		private float z;
		
		public Offset(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public float getX() {
			return x;
		}
		
		public void setX(float x) {
			this.x = x;
		}
		
		public float getY() {
			return y;
		}
		
		public void setY(float y) {
			this.y = y;
		}
		
		public float getZ() {
			return z;
		}
		
		public void setZ(float z) {
			this.z = z;
		}
		
	}

	public enum ParticleEffectType {
		HUGE_EXPLOSION("hugeexplosion"),		
		LARGE_EXPLODE("largeexplode"),
		FIREWORKS_SPARK("fireworksSpark"),
		BUBBLE("bubble"),
		SUSPEND("suspend"),
		DEPTH_SUSPEND("depthSuspend"),
		TOWN_AURA("townaura"),
		CRIT("crit"),
		MAGIC_CRIT("magicCrit"),
		SMOKE("smoke"),
		MOB_SPELL("mobSpell"),
		MOB_SPELL_AMBIENT("mobSpellAmbient"),
		SPELL("spell"),
		INSTANT_SPELL("instantSpell"),
		WITCH_MAGIC("witchMagic"),
		NOTE("note"),
		PORTAL("portal"),
		ENCHANTMENT_TABLE("enchantmenttable"),
		EXPLODE("explode"),
		FLAME("flame"),
		LAVA("lava"),
		FOOTSTEP("footstep"),
		SPLASH("splash"),
		WAKE("wake"),
		LARGE_SMOKE("largesmoke"),
		CLOUD("cloud"),
		RED_DUST("reddust"),
		SNOWBALL_POOF("snowballpoof"),
		DRIP_WATER("dripWater"),
		DRIP_LAVA("dripLava"),
		SNOW_SHOVEL("snowshovel"),
		SLIME("slime"),
		HEART("heart"),
		ANGRY_VILLAGER("angryVillager"),
		HAPPY_VILLAGER("happyVillager");
		
		private String name;
		
		private ParticleEffectType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}

	public static void spawn(Player p, ParticleEffectType type, Offset offset, float data, int count) {
		spawn(p, type, p.getLocation(), offset, data, count);
	}
	
	public static void spawn(Player p, ParticleEffectType type, Location loc, Offset offset, float data, int count) {
		PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(type.getName(), (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), offset.getX(), offset.getY(), offset.getZ(), data, count);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(particle);
	}	
	
	public static void broadcast(ParticleEffectType type, Offset offset, float data, int count) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			spawn(p, type, p.getLocation(), offset, data, count);
		}
	}
	
	public static void broadcast(ParticleEffectType type, Location loc, Offset offset, float data, int count) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			spawn(p, type, loc, offset, data, count);
		}
	}
	
}
