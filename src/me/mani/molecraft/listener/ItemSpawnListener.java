package me.mani.molecraft.listener;

import java.lang.reflect.Field;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.BlockManager;
import me.mani.molecraft.manager.MainManager;
import net.minecraft.server.v1_8_R2.EntityItem;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftItem;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSpawnListener extends MoleCraftListener {
	
	private Field ageField;
	
	public ItemSpawnListener(MainManager mainManager) {
		super(mainManager);
		try {
			ageField = EntityItem.class.getDeclaredField("age");
			ageField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {}	
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent ev) {
		Item item = ev.getEntity();
		EntityItem itemHandle = ((EntityItem) ((CraftItem) item).getHandle());
		try {
			ageField.set(itemHandle, 5400);
		} catch (Exception e) { 
			item.remove();
		}
		if (item.getItemStack().getType() == Material.STRING)
			item.setItemStack(new ItemStack(Material.WEB));
		if (BlockManager.isBreakable(item.getItemStack().getType()))
			item.setItemStack(BlockManager.getDrop(item.getItemStack().getType()));
	}

}
