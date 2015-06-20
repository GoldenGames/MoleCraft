package me.mani.molecraft.util;

import java.util.function.Consumer;

import me.mani.molecraft.MoleCraft;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class PermanentMessenger {
	
	private static String message;
	
	private static BukkitTask task;
	private static Consumer<String> consumer;
	
	public static String getMessage() {
		return message;
	}
	
	public static void setMessage(String message) {
		PermanentMessenger.message = message;
	}
	
	public static void start(Consumer<String> consumer, long delay) {
		PermanentMessenger.consumer = consumer;
		task = Bukkit.getScheduler().runTaskTimer(MoleCraft.getInstance(), () -> PermanentMessenger.consumer.accept(message), 0L, delay);
	}
	
	public static void stop() {
		PermanentMessenger.consumer = null;
		task.cancel();
	}

}
