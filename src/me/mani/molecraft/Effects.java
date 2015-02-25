package me.mani.molecraft;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Effects {
	
	public static void playAll(Sound sound) {
		if (sound == null)
			return;
		for (Player player : Bukkit.getOnlinePlayers())
			play(player, sound);
	}
	
	public static void play(Player player, Sound sound) {
		if (sound == null)
			return;
		player.playSound(player.getLocation(), sound, 1, 1);
	}
	
	public static void showAll(Effect effect, Location location) {
		showAll(effect, location, 1, 10);
	}
	
	public static void showAll(Effect effect, Location location, int size, int particleCount) {
		if (effect == null)
			return;
		for (Player player : Bukkit.getOnlinePlayers())
			show(player, effect, location, size, particleCount);
	}
	
	public static void show(Player player, Effect effect, Location location) {
		show(player, effect, location, 1, 10);
	}
	
	public static void show(Player player, Effect effect, Location location, int size, int particleCount) {
		player.spigot().playEffect(location, effect, 0, 0, size, size, size, 1, particleCount, 64);
	}

}
