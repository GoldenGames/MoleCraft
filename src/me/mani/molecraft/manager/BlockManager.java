package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BlockManager {
	
	private static Map<Material, ItemStack> breakable = new HashMap<>();
	private static List<Material> placeable = new ArrayList<>();
	public static Set<Block> allBlocks = new HashSet<>();
	
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
		breakable.put(Material.DIRT, ItemUtil.createItem(new ItemStack(Material.DIRT), "§7« §6Erde §7»"));
		breakable.put(Material.TORCH, ItemUtil.createItem(new ItemStack(Material.TORCH), "§7« §6Fackeln §7»"));
		breakable.put(Material.TNT, ItemUtil.createItem(new ItemStack(Material.TNT), "§7« §6Dynamit §7»"));
		breakable.put(Material.WEB, ItemUtil.createItem(new ItemStack(Material.WEB), "§7« §6Spinnenweben §7»"));
		breakable.put(Material.LADDER, ItemUtil.createItem(new ItemStack(Material.LADDER), "§7« §6Leitern §7»"));
		
		placeable.add(Material.DIRT);
		placeable.add(Material.TORCH);
		placeable.add(Material.TNT);
		placeable.add(Material.REDSTONE_TORCH_ON);
		placeable.add(Material.WEB);
		placeable.add(Material.LADDER);
		placeable.add(Material.FIRE);
	}
	
}
