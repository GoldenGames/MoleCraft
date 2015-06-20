package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;

public class InventoryCloseListener extends MoleCraftListener {

	public InventoryCloseListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent ev) {
		
		if (ev.getView().getType() == InventoryType.ENCHANTING)
			((EnchantingInventory) ev.getInventory()).setSecondary(null);
		else if (ev.getInventory().getType() == InventoryType.CHEST && ev.getInventory().getHolder() != null) {
			Chest chest = (Chest) ev.getInventory().getHolder();
			if (!gameManager.chestManager.isOpened(chest)) {
				gameManager.chestManager.openChest(chest);
				StatsListener.onStatsChange(gameManager, new StatsEvent((Player) ev.getPlayer(), StatsEventType.CHEST, 1));
			}
		}
		
	}

}
