package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.util.NickUtil;
import me.mani.molecraft.util.RankHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener extends MoleCraftListener {
	
	public AsyncPlayerChatListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent ev) {
		
		MoleCraftPlayer moleCraftPlayer = MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer());
		
		if (GameState.getGameState() == GameState.LOBBY)
			if (NickUtil.isNicked(ev.getPlayer()))
				ev.setFormat("§9" + ev.getPlayer().getDisplayName() + " §8\u00BB §7%2$s");
			else
				ev.setFormat(RankHandler.getPrefix(ev.getPlayer()) + ev.getPlayer().getDisplayName() + " §8\u00BB §7%2$s");
		else
			ev.setFormat((moleCraftPlayer.isSpectator() ? "§cTOT " : "") + gameManager.teamManager.getTeam(ev.getPlayer()).getChatColor() + ev.getPlayer().getDisplayName() + " §8\u00BB §7%2$s");
		
	}

}
