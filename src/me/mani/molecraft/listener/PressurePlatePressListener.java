package me.mani.molecraft.listener;

import java.util.ArrayList;
import java.util.List;

import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.animation.FireFontain;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PressurePlatePressListener extends MoleCraftListener {
	
	public PressurePlatePressListener(MainManager mainManager) {
		super(mainManager);
	}

	private List<Player> pressed = new ArrayList<>();
	
	@EventHandler
	public void onPressure(PlayerInteractEvent ev) {
		if (ev.getAction() != Action.PHYSICAL)
			return;
		if (ev.getClickedBlock().getType() != Material.GOLD_PLATE)
			return;
		if (!pressed.contains(ev.getPlayer()))
			handlePressure(ev.getPlayer());
	}
	
	private void handlePressure(Player player) {
		// TODO: Move this somewhere else
		pressed.add(player);
		Messenger.sendAll("§b" + player.getName() + " §7hat den Parkour geschafft!");
		FireFontain fireFontain = new FireFontain(player.getLocation());
		fireFontain.run();
		gameManager.lobbyManager.handleParkourSuccess(player);
	}

}
