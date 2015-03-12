package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.List;

import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.ArenaMapPack;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.manager.SetupManager.SetupException;
import me.mani.molecraft.util.BlockMath;
import me.mani.molecraft.util.RandomUtil;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ArenaMapManager {
	
	private MoleCraft moleCraft;
	private GameManager gameManager;
	private ArenaMapPack arenaMapPack;
	
	public ArenaMapManager(MoleCraft moleCraft, ArenaMapPack arenaMapPack) {
		this.moleCraft = moleCraft;
		this.gameManager = moleCraft.gameManager;
		this.arenaMapPack = arenaMapPack;
	}

	public ArenaMap setupArenaMap(World mainWorld, int id) throws SetupException {
		if (!arenaMapPack.containsArenaMapInfo(id))
			throw new SetupException("The specified arena map doesn't exist");
		ArenaMap arenaMap = ArenaMap.loadArenaMap(mainWorld, arenaMapPack.getArenaMapInfo(id).getMapInfoPath());
		for (int i = 0; i < 7; i++) // Load spawn locations
			gameManager.locationManager.setSpawnLocation(i, ConvertUtil.toLocation(arenaMap.getMapInfo().getString("spawn" + i), arenaMap.getWorld()));
		for (Chunk chunk : BlockMath.getChunks(arenaMap.getCornerLocation(0), arenaMap.getCornerLocation(1))) { // Load unloaded chunks
			if (!chunk.isLoaded())
				chunk.load();
		}		
		return arenaMap;
	}
	
	public ChestManager addChests(ArenaMap arenaMap, int count) {
		List<Block> dirtBlocks = BlockMath.getBlocks(arenaMap.getCornerLocation(0), arenaMap.getCornerLocation(1), Material.DIRT);
		List<Block> chestBlocks = new ArrayList<>();
		for (int i = 1; i < count; i++) {
			int randomInteger = RandomUtil.getRandomInteger(0, dirtBlocks.size() - 1);
			chestBlocks.add(dirtBlocks.get(randomInteger));
			dirtBlocks.remove(randomInteger);
		}
		return new ChestManager(chestBlocks);
	}
	
	public ArenaMapPack getArenaMapPack() {
		return arenaMapPack;
	}
	
}
