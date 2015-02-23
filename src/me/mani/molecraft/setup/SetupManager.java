package me.mani.molecraft.setup;

import java.util.ArrayList;
import java.util.List;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.Manager;
import me.mani.molecraft.game.LocationManager.SpecialLocation;
import me.mani.molecraft.util.BlockMath;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class SetupManager extends Manager {
	
	private static World world;
	private Location loc1;
	private Location loc2;
	
	private List<Block> dirtBlocks;
	private List<Chest> chestBlocks = new ArrayList<>();
	
	public void setup() {
		loadData();
		
		loadChunks();
		loadDirtBlocks();
		resetChests();
		addChests(30);
		
		ChestManager chestManager = new ChestManager(chestBlocks);
		chestManager.fillAll();
	}
	
	private void loadData() {
		world = Bukkit.getWorld((String) GoldenAPI.getManager().get("molecraft_arena", "id", 1, "world"));
		loc1 = ConvertUtil.toLocation((String) GoldenAPI.getManager().get("molecraft_arena", "id", 1, "loc1"), world);
		loc2 = ConvertUtil.toLocation((String) GoldenAPI.getManager().get("molecraft_arena", "id", 1, "loc2"), world);
	}
	
	private void loadChunks() {
		for (Location loc : BlockMath.getLocations(loc1, loc2)) {
			if (!loc.getChunk().isLoaded())
				loc.getChunk().load();
		}
		Chunk spawnChunk = SpecialLocation.LOBBY_SPAWN.getChunk();
		if (!spawnChunk.isLoaded())
			spawnChunk.load();
	}
	
	private void loadDirtBlocks() {
		this.dirtBlocks = BlockMath.getBlocks(loc1, loc2, Material.DIRT);
	}
	
	private void resetChests() {
		for (Block b : BlockMath.getBlocks(loc1, loc2, Material.CHEST)) {
			((Chest) b.getState()).getBlockInventory().clear();
			b.setType(Material.DIRT);
		}
	}
	
	private void addChests(int count) {
		for (int i = 1; i < count; i++) {
			int random = getRandomInteger(0, dirtBlocks.size() - 1);
			Block b = dirtBlocks.get(random);
			b.setType(Material.CHEST);
			dirtBlocks.remove(random);
			chestBlocks.add((Chest) b.getState());
		}
	}
	
	private int getRandomInteger(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	
	public static World getWorld() {
		return world;
	}
	
}
