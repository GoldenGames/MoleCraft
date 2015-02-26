package me.mani.molecraft;

import java.util.function.Consumer;

import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class MoleCraftListener implements Listener {
	
	private MainManager mainManager = MoleCraft.getInstance().gameManager == null ? MoleCraft.getInstance().gameManager : MoleCraft.getInstance().debugManager;
	/** Can only be used if a game manager is active, Otherwise it's null */
	protected GameManager gameManager = mainManager instanceof GameManager ? (GameManager) mainManager : null;
	/** Can only be used if a debug manager is active. Otherwise it's null */
	protected DebugManager debugManager = mainManager instanceof DebugManager ? (DebugManager) mainManager : null;
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
