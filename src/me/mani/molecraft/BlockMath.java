package me.mani.molecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockMath {

	public static List<Block> getBlocks(Location loc1, Location loc2) {
		return getBlocks(loc1, loc2, Material.values());
	}
	
	public static List<Block> getBlocks(Location loc1, Location loc2, Material... pattern) {
		if (!loc1.getWorld().equals(loc2.getWorld()))
			return null;
		
		World world = loc1.getWorld();
		
		List<Block> allBlocks = new ArrayList<>();
		List<Material> patternList = Arrays.asList(pattern); 
		
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		for (int x = minX; x < maxX; x++)
			for (int y = minY; y < maxY; y++)
				for (int z = minZ; z < maxZ; z++)
					if (patternList.contains(world.getBlockAt(x, y, z).getType()))
						allBlocks.add(world.getBlockAt(x, y, z));
		return allBlocks;
	}
	
	public static List<Location> getLocations(Location loc1, Location loc2) {
		if (!loc1.getWorld().equals(loc2.getWorld()))
			return null;
		
		World world = loc1.getWorld();
		
		List<Location> allLocations = new ArrayList<>(); 
		
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		for (int x = minX; x < maxX; x++)
			for (int y = minY; y < maxY; y++)
				for (int z = minZ; z < maxZ; z++)
					allLocations.add(new Location(world, x, y, z));
		return allLocations;
	}
	
	public static int countBlocks(Location loc1, Location loc2) {
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		return (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1);
	}
}
