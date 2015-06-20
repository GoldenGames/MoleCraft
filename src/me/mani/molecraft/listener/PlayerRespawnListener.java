package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener extends MoleCraftListener {
	
	public PlayerRespawnListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent ev) {
		
		Player player = ev.getPlayer();
		MoleCraftPlayer moleCraftPlayer = MoleCraftPlayer.getMoleCraftPlayer(player);
		
		ev.setRespawnLocation(moleCraftPlayer.getDeathLocation());
				
	}

}
