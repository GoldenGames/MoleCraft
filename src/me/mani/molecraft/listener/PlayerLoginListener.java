package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.util.NickFetcher;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener extends MoleCraftListener {
	
	public PlayerLoginListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent ev) {
		
		if (GameState.getGameState() == GameState.SETUP || GameState.getGameState() == GameState.SHUTDOWN) {
			ev.setResult(Result.KICK_OTHER);
			ev.setKickMessage("§cDer Server " + (GameState.getGameState() == GameState.SETUP ? "startet" : "stoppt") + ".");
		}
		else if (ev.getResult() == Result.KICK_FULL)
			ev.setKickMessage("§cDer Server ist voll. Als §ePremium §c kannst du auch volle Server betreten.");
		else if (NickFetcher.hasAutoNick(ev.getPlayer().getName()))
			Messenger.send(ev.getPlayer(), "Die Nickfunktion ist derzeit nicht verfügbar.");
		
	}

}
