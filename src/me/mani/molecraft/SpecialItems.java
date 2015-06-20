package me.mani.molecraft;

import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;


public class SpecialItems {

	public static void openSpecialChest(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 9 * 3, "Spezialkiste");
		ItemStack spacerItem = ItemUtil.createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8), " "); 
		
		inventory.setItem(15, SpecialItemType.MINI_EXPLOSION.getItemStack());
		
		for (int i = 0; i < inventory.getSize(); i++)
			if (inventory.getItem(i) == null)
				inventory.setItem(i, spacerItem);
		
		player.openInventory(inventory);
	}
	
	public static void handleSpecialItem(SpecialItemType specialItemType, Location location) {
		Block block = location.getBlock();
		switch (specialItemType) {
		case MINI_EXPLOSION:
			CountdownManager.createCountdown((ev) -> {
				BlockState blockState = block.getState();
				MaterialData data = blockState.getData();
				if (ev.getCurrentNumber() % 2 == 0)
					blockState.setType(Material.REDSTONE_TORCH_OFF);
				blockState.setData(data);
				blockState.update(true, false);
				ev.setSound(Sound.NOTE_PLING);
				
			}, (ev) -> {
				
				block.setType(Material.AIR);
				location.getWorld().createExplosion(location, 2f, false);
				
			}, 4, 0, 5L);
		}
	}
	
	public static enum SpecialItemType {
		MINI_EXPLOSION (ItemUtil.createItem(new ItemStack(Material.REDSTONE_TORCH_ON), "Minizündung"));
		
		private ItemStack itemStack;
		
		private SpecialItemType(ItemStack itemStack) {
			this.itemStack = itemStack;
		}
		
		public ItemStack getItemStack() {
			return itemStack;
		}
	}
	
}
