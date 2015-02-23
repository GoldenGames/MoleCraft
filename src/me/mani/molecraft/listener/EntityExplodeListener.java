package me.mani.molecraft.listener;

import java.util.Iterator;

import me.mani.molecraft.MoleCraftListener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener extends MoleCraftListener {
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent ev) {
		
		Iterator<Block> it = ev.blockList().iterator();
		while (it.hasNext()) {
			Block b = it.next();
			if (b.getType() != Material.DIRT)
				it.remove();
		}
		
	}

}
