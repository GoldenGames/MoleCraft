package me.mani.molecraft.listener;

import java.util.Iterator;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener extends MoleCraftListener {
	
	public EntityExplodeListener(MainManager mainManager) {
		super(mainManager);
	}

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
