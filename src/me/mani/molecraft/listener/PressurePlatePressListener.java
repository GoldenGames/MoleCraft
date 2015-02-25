package me.mani.molecraft.listener;

import java.util.ArrayList;
import java.util.List;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.animation.FireFontain;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PressurePlatePressListener extends MoleCraftListener {
	
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
	
	private void handlePressure(Player p) {
		pressed.add(p);
		Bukkit.broadcastMessage("§7[§dMoleCraft§7] §e" + p.getName() + " §6hat den Parkour geschafft!");
		FireFontain fireFontain = new FireFontain(p.getLocation());
		fireFontain.run();
	}

}
