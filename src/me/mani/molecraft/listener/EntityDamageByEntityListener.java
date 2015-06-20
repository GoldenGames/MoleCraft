package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener extends MoleCraftListener {

	public EntityDamageByEntityListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent ev) {
		
		if (ev.getEntity() instanceof Player && MoleCraftPlayer.getMoleCraftPlayer((Player) ev.getEntity()).isSpectator())
			ev.setCancelled(true);
		if (ev.getDamager() instanceof Player && MoleCraftPlayer.getMoleCraftPlayer((Player) ev.getDamager()).isSpectator())
			ev.setCancelled(true);
		
	}

}
