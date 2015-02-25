package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class BlockManager {
	
	private static List<Material> breakable = new ArrayList<>();
	private static List<Material> placeable = new ArrayList<>();
	
	public static boolean isBreakable(Material material) {
		return breakable.contains(material);
	}
	
	public static boolean isPlaceable(Material material) {
		return placeable.contains(material);
	}

	static {
		breakable.add(Material.DIRT);
		breakable.add(Material.TORCH);
		breakable.add(Material.TNT);
		breakable.add(Material.REDSTONE_TORCH_ON);
		breakable.add(Material.WEB);
		breakable.add(Material.LADDER);
		
		placeable.add(Material.DIRT);
		placeable.add(Material.TORCH);
		placeable.add(Material.TNT);
		placeable.add(Material.REDSTONE_TORCH_ON);
		placeable.add(Material.WEB);
		placeable.add(Material.LADDER);
	}
	
}
