package me.mani.molecraft;

import java.util.function.Consumer;

import me.mani.molecraft.manager.GameManager;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class MoleCraftListener implements Listener {
	
	protected GameManager gameManager = MoleCraft.getInstance().gameManager;
	private static Consumer<Event> currentConsumer;
	
	public MoleCraftListener() {
		MoleCraft.getInstance().getServer().getPluginManager().registerEvents(this, MoleCraft.getInstance());
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
