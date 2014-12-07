package me.mani.molecraft.debug;

import me.mani.molecraft.debug.DebugManager.MessageType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener implements Listener {

	private SelectionTool tool;
	
	public SelectionListener(SelectionTool tool) {
		this.tool = tool;
	}
	
	@EventHandler
	public void onSelect(PlayerInteractEvent ev) {
		Player p = ev.getPlayer();
		if (p.getItemInHand() == null || p.getItemInHand().getType() == null || p.getItemInHand().getType() != tool.getSelectionItem())
			return;
		if (ev.getAction() == Action.LEFT_CLICK_BLOCK) {
			tool.setFirstLocation(ev.getClickedBlock().getLocation());
			DebugManager.send(p, MessageType.INFO, "Erste Location gesetzt! (" + tool.getCount() + ")");
		}
		else if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
			tool.setSecondLocation(ev.getClickedBlock().getLocation());
			DebugManager.send(p, MessageType.INFO, "Zweite Location gesetzt! (" + tool.getCount() + ")");
		}
		ev.setCancelled(true);
	}
}
