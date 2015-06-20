package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.util.NickUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

public class PlayerChatTabCompleteListener extends MoleCraftListener {

	public PlayerChatTabCompleteListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent ev) {
		
		ev.getTabCompletions().removeIf((name) -> NickUtil.isNicked(Bukkit.getPlayer(name)));	
		
		NickUtil.getNicks().forEach((name) -> {
			if (name.startsWith(ev.getLastToken()))
				ev.getTabCompletions().add(name);
		});
		
	}

}
