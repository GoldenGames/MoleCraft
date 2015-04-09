package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlockManager {
	
	private static Map<Material, ItemStack> breakable = new HashMap<>();
	private static List<Material> placeable = new ArrayList<>();
	
	public static boolean isBreakable(Material material) {
		return breakable.containsKey(material);
	}
	
	public static ItemStack getDrop(Material material) {
		return breakable.get(material);
	}
	
	public static boolean isPlaceable(Material material) {
		return placeable.contains(material);
	}

	static {
		breakable.put(Material.DIRT, ItemUtil.createItem(new ItemStack(Material.DIRT), "§dErde"));
		breakable.put(Material.TORCH, ItemUtil.createItem(new ItemStack(Material.TORCH), "§6Fackel"));
		breakable.put(Material.TNT, ItemUtil.createItem(new ItemStack(Material.TNT), "§4Dynamit"));
		breakable.put(Material.REDSTONE_TORCH_ON, ItemUtil.createItem(new ItemStack(Material.REDSTONE_TORCH_ON), "§4Zünder"));
		breakable.put(Material.WEB, ItemUtil.createItem(new ItemStack(Material.WEB), "§fSpinnenweben"));
		breakable.put(Material.LADDER, ItemUtil.createItem(new ItemStack(Material.LADDER), "§fLeiter"));
		
		placeable.add(Material.DIRT);
		placeable.add(Material.TORCH);
		placeable.add(Material.TNT);
		placeable.add(Material.REDSTONE_TORCH_ON);
		placeable.add(Material.WEB);
		placeable.add(Material.LADDER);
		placeable.add(Material.FIRE);
	}
	
}
