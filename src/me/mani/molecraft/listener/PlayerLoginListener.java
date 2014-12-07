package me.mani.molecraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import me.mani.molecraft.GameState;
import me.mani.molecraft.util.AdvListener;

public class PlayerLoginListener extends AdvListener {
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent ev) {
		
		if (getState() != GameState.LOBBY) {
			ev.setResult(Result.KICK_OTHER);
			ev.setKickMessage("§cDas Spiel hat schon begonnen!");
		}
		
	}

}
