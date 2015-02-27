package me.mani.molecraft.selection;

import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener extends MoleCraftListener {

	private SelectionTool tool;
	
	public SelectionListener(MainManager mainManager, SelectionTool tool) {
		super(mainManager);
		this.tool = tool;
	}
	
	@EventHandler
	public void onSelect(PlayerInteractEvent ev) {
		Player p = ev.getPlayer();
		if (p.getItemInHand() == null || p.getItemInHand().getType() == null || p.getItemInHand().getType() != tool.getSelectionItem())
			return;
		if (ev.getAction() == Action.LEFT_CLICK_BLOCK) {
			tool.setFirstLocation(ev.getClickedBlock().getLocation());
			Messenger.send(p, "Erste Location gesetzt! (" + tool.getCount() + ")");
		}
		else if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
			tool.setSecondLocation(ev.getClickedBlock().getLocation());
			Messenger.send(p, "Zweite Location gesetzt! (" + tool.getCount() + ")");
		}
		ev.setCancelled(true);
	}
}
