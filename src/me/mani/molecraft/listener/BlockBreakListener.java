package me.mani.molecraft.listener;

import java.util.ArrayList;

import me.mani.molecraft.Effects;
import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.BlockManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.util.WoolLocation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener extends MoleCraftListener {
	
	public static boolean superBlockBreak;
	
	public BlockBreakListener(MainManager mainManager) {
		super(mainManager);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY)
			ev.setCancelled(true);
		
		else if (GameState.getGameState() == GameState.INGAME) {
			
			if (!MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).isSpectator() && BlockManager.isBreakable(ev.getBlock().getType())) {
				if (superBlockBreak) {
					for (int x = -1; x <= 1; x++)
						for (int y = -1; y <= 1; y++)
							for (int z = -1; z <= 1; z++) {
								Location location = ev.getBlock().getLocation().add(x, y, z);
								if (BlockManager.isBreakable(location.getBlock().getType())) {
									location.getBlock().breakNaturally();
									Effects.playAll(Sound.DIG_GRAVEL);
								}
							}
				}
				return;
			}
			else if (ev.getBlock().getType() == Material.WOOL && !MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).isIngame()) {
				if (GameManager.destroidWool.get(ev.getPlayer()) == null)
					GameManager.destroidWool.put(ev.getPlayer(), new ArrayList<WoolLocation>());
				GameManager.destroidWool.get(ev.getPlayer()).add(new WoolLocation(ev.getBlock().getLocation(), ev.getBlock().getData()));
				ev.getBlock().setType(Material.AIR);
			}
			ev.setCancelled(true);
		}			
		else
			ev.setCancelled(true);
	}

}
