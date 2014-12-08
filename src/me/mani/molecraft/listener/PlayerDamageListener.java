package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.game.GameManager;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageListener extends AdvListener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if (!(ev.getEntity() instanceof Player))
			return;
		
		Player p = (Player) ev.getEntity();
		
		if (getState() != GameState.INGAME)
			ev.setCancelled(true);
		else if (ev.getCause() == DamageCause.SUFFOCATION)
			ev.setCancelled(true);
		else if (!GameManager.isIngame(p))
			ev.setCancelled(true);
	}

}
