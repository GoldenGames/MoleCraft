package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Message;
import me.mani.molecraft.game.LobbyManager;
import me.mani.molecraft.util.AdvListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends AdvListener {
	
	@EventHandler
	public void onLeave(PlayerQuitEvent ev) {
		
		if (getState() == GameState.LOBBY) {
			ev.setQuitMessage(Message.PREFIX + "§c[<<<] §e" + ev.getPlayer().getName());
			LobbyManager.removePlayer();
		}
		
	}

}
