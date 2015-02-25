package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends MoleCraftListener {
	
	@EventHandler
	public void onLeave(PlayerQuitEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY) {
			ev.setQuitMessage("§c[<<<] §e" + ev.getPlayer().getName());
			gameManager.lobbyPlayerManager.removePlayer();
		}
		
	}

}
