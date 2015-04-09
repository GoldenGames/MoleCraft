package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.DeathManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends MoleCraftListener {
	
	public PlayerDeathListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent ev) {	
		ev.setDroppedExp(0);
		ev.setNewTotalExp(0);	
		ev.setDeathMessage("");
		DeathManager.handlePlayerDeath(ev.getEntity());
	}

}
