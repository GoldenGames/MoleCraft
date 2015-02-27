package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener extends MoleCraftListener {
	
	public PlayerLoginListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent ev) {
		
		if (GameState.getGameState() != GameState.LOBBY) {
			ev.setResult(Result.KICK_OTHER);
			ev.setKickMessage("§cDas Spiel hat schon begonnen!");
		}
		
	}

}
