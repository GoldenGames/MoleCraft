package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageListener extends MoleCraftListener {
	
	public PlayerDamageListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if (!(ev.getEntity() instanceof Player))
			return;
		
		Player p = (Player) ev.getEntity();
		
		if (GameState.getGameState() != GameState.INGAME || ev.getCause() == DamageCause.SUFFOCATION || !GameManager.isIngame(p))
			ev.setCancelled(true);
	}

}
