package me.mani.molecraft.listener;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends MoleCraftListener {

	public PlayerMoveListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent ev) {
		
		if (GameState.getGameState() == GameState.WARM_UP) {
			Player p = ev.getPlayer();
			if (ev.getFrom().getX() != ev.getTo().getX() || ev.getFrom().getZ() != ev.getTo().getZ())
				p.teleport(ev.getFrom());
		}
		
		else if (GameState.getGameState() == GameState.INGAME) {
			Player p = ev.getPlayer();
			MoleCraftPlayer moleCraftPlayer = MoleCraftPlayer.getMoleCraftPlayer(p);
			if (ev.getTo().subtract(0, 1, 0).getBlock().getType() == Material.DIRT && !moleCraftPlayer.isIngame()) {
				moleCraftPlayer.setIngame(true);
				gameManager.replaceWool(p);
				Messenger.send(p, "Viel Glück!");
			}
		}
		
	}

}
