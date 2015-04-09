package me.mani.molecraft.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.DeathManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerQuitListener extends MoleCraftListener {
	
	public PlayerQuitListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent ev) {
		
		MoleCraftPlayer.removeMoleCraftPlayer(MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()));
		
		if (GameState.getGameState() == GameState.LOBBY) {
			ev.setQuitMessage("§c[<<<] §e" + ev.getPlayer().getName());
			gameManager.lobbyManager.removePlayer();
		}
		
		else if (GameState.getGameState() == GameState.INGAME) {
			List<ItemStack> drops = new ArrayList<>(Arrays.asList(ev.getPlayer().getInventory().getContents()));
			drops.addAll(Arrays.asList(ev.getPlayer().getInventory().getArmorContents()));
			drops.forEach((itemStack) -> {
				if (itemStack != null)
					ev.getPlayer().getWorld().dropItemNaturally(ev.getPlayer().getLocation(), itemStack);
			});
			DeathManager.handlePlayerDeath(ev.getPlayer());
		}	
	}
}
