package me.mani.molecraft;

import java.util.function.Consumer;

import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class MoleCraftListener implements Listener {
	
	protected GameManager gameManager;
	protected DebugManager debugManager;
	private static Consumer<Event> currentConsumer;
	
	public MoleCraftListener(MainManager mainManager) {
		MoleCraft.getInstance().getServer().getPluginManager().registerEvents(this, MoleCraft.getInstance());
		if (mainManager instanceof GameManager)
			this.gameManager = (GameManager) mainManager;
		else if (mainManager instanceof DebugManager)
			this.debugManager = (DebugManager) mainManager;
	}
	
	protected void accept(Event ev, boolean cancel) {
		if (currentConsumer != null) {
			currentConsumer.accept(ev);
			currentConsumer = null;
			if (cancel && ev instanceof Cancellable)
				((Cancellable) ev).setCancelled(true);
		}
	}
	
	public static void listen(Consumer<Event> consumer) {
		currentConsumer = consumer;
	}
	
}
