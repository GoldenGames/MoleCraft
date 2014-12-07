package me.mani.molecraft.listener;

import java.util.ArrayList;

import me.mani.molecraft.GameState;
import me.mani.molecraft.config.BlockManager;
import me.mani.molecraft.game.GameManager;
import me.mani.molecraft.util.AdvListener;
import me.mani.molecraft.util.WoolLocation;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener extends AdvListener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		
		if (getState() == GameState.LOBBY)
			ev.setCancelled(true);
		
		else if (getState() == GameState.INGAME) {
			
			if (BlockManager.isBreakable(ev.getBlock().getType()))
				return;
			
			else if (ev.getBlock().getType() == Material.WOOL && !GameManager.isIngame(ev.getPlayer())) {
				if (GameManager.destroidWool.get(ev.getPlayer()) == null)
					GameManager.destroidWool.put(ev.getPlayer(), new ArrayList<WoolLocation>());
				GameManager.destroidWool.get(ev.getPlayer()).add(new WoolLocation(ev.getBlock().getLocation(), ev.getBlock().getData()));
				ev.getBlock().setType(Material.AIR);
				ev.setCancelled(true);
			}
			
			ev.setCancelled(true);
		}	
		else
			ev.setCancelled(true);
	}

}
