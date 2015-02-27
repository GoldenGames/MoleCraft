package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends MoleCraftListener {
	
	public PlayerQuitListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY) {
			ev.setQuitMessage("§c[<<<] §e" + ev.getPlayer().getName());
			gameManager.lobbyPlayerManager.removePlayer();
		}
		
	}

}
