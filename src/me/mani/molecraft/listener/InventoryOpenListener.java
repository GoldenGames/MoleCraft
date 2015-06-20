package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class InventoryOpenListener extends MoleCraftListener {

	public InventoryOpenListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent ev) {
		
		if (ev.getView().getType() == InventoryType.ENCHANTING)
			((EnchantingInventory) ev.getInventory()).setSecondary(new ItemStack(Material.INK_SACK, 3, (short) 4));
		
	}

}
