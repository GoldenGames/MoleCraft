package me.mani.molecraft.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.Constants;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.commands.StatsCommand;
import me.mani.molecraft.listener.BlockBreakListener;
import me.mani.molecraft.listener.BlockPlaceListener;
import me.mani.molecraft.listener.EntityExplodeListener;
import me.mani.molecraft.listener.InventoryClickListener;
import me.mani.molecraft.listener.PlayerDamageListener;
import me.mani.molecraft.listener.PlayerDeathListener;
import me.mani.molecraft.listener.PlayerInteractListener;
import me.mani.molecraft.listener.PlayerItemDropPickupListener;
import me.mani.molecraft.listener.PlayerJoinListener;
import me.mani.molecraft.listener.PlayerLoginListener;
import me.mani.molecraft.listener.PlayerMoveListener;
import me.mani.molecraft.listener.PlayerQuitListener;
import me.mani.molecraft.listener.PlayerRespawnListener;
import me.mani.molecraft.listener.PressurePlatePressListener;
import me.mani.molecraft.util.BlockMath;
import me.mani.molecraft.util.RandomUtil;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

public class SetupManager {
	
	public MoleCraft moleCraft;
	private GameManager gameManager;
	
	private DatabaseManager sql;
	private ArenaMap arenaMap;
	private LocationManager locationManager;
	private ChestManager chestManager;
	
	public SetupManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
		this.gameManager = moleCraft.gameManager;
	}
	
	public boolean setup() {
		try {
			arenaMap = loadArenaMap();
			System.out.println("Loaded Map");
			sql = loadSQL();
			System.out.println("Loaded MySQL");
			locationManager = loadLocationManager();
			System.out.println("Loaded Locations");
			loadChunks();
			System.out.println("Loaded Chunks");
			chestManager = addChests(30);
			chestManager.fillAll();
			System.out.println("Placed and filled chests");
			registerListener();
			registerCommands();
			System.out.println("Registered listeners and commands");
		}
		catch (SetupException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	private ArenaMap loadArenaMap() throws SetupException {
		World world = Bukkit.getWorld("map");
		if (world == null)
			throw new SetupException("The world loaded, doesn't exists");
		File mapInfoFile = new File(world.getWorldFolder(), "mapInfo.yml");
		if (!mapInfoFile.exists())
			throw new SetupException("The map info file doesn't exists");
		YamlConfiguration mapInfo = YamlConfiguration.loadConfiguration(mapInfoFile);
		String displayName = mapInfo.getString(Constants.DISPLAY_NAME, "");
		String builderName = mapInfo.getString(Constants.BUILDER_NAME, "");
		List<String> cornerLocations = mapInfo.getStringList(Constants.CORNER_LOCATION);
		if (displayName.equals("") || builderName.equals("") || cornerLocations.isEmpty() || cornerLocations.size() < 2)
			throw new SetupException("The config isn't valid");
		Location cornerLocation1 = ConvertUtil.toLocation(cornerLocations.get(0), world);
		Location cornerLocation2 = ConvertUtil.toLocation(cornerLocations.get(1), world);
		if (cornerLocation1 == null || cornerLocation2 == null)
			throw new SetupException("One of the locations, or both, doesn't ecists");
		return new ArenaMap(world, displayName, builderName, mapInfo, cornerLocation1, cornerLocation2);
	}
	
	private DatabaseManager loadSQL() throws SetupException {
		GoldenAPI.setPlayerNameWatching(true);
		DatabaseManager sql = GoldenAPI.connectToSQL(moleCraft);
		if (!sql.isAvaible(Constants.MAIN_TABLE, "key", Constants.CONNECTION_TEST))
			throw new SetupException("The MySQL database ist not avaible");
		return sql;	
	}
	
	private LocationManager loadLocationManager() throws SetupException {
		Location lobbySpawnLocation = 
				ConvertUtil.toLocation((String) sql.get(Constants.MAIN_TABLE, "key", Constants.LOBBY_SPAWN, "value"), arenaMap.getWorld());
		Location parkourSpawnLocation =
				ConvertUtil.toLocation((String) sql.get(Constants.MAIN_TABLE, "key", Constants.PARKOUR_SPAWN, "value"), arenaMap.getWorld());		
		Location statsDisplayLocation =
				ConvertUtil.toLocation((String) sql.get(Constants.MAIN_TABLE, "key", Constants.STATS_DISPLAY, "value"), arenaMap.getWorld());
		Map<Integer, Location> spawnLocations = new HashMap<>();
		for (int i = 0; i < 7; i++)
			spawnLocations.put(i, ConvertUtil.toLocation(arenaMap.getMapInfo().getString("spawn" + i), arenaMap.getWorld()));
		return new LocationManager(arenaMap.getWorld(), lobbySpawnLocation, parkourSpawnLocation, statsDisplayLocation, spawnLocations);	
	}
	
	private void loadChunks() {
		for (Chunk chunk : BlockMath.getChunks(arenaMap.getCornerLocation(0), arenaMap.getCornerLocation(1))) {
			if (!chunk.isLoaded())
				chunk.load();
		}
		Chunk spawnChunk = locationManager.LOBBY_SPAWN.getChunk();
		if (!spawnChunk.isLoaded())
			spawnChunk.load();
	}
		
	private ChestManager addChests(int count) {
		List<Block> dirtBlocks = BlockMath.getBlocks(arenaMap.getCornerLocation(0), arenaMap.getCornerLocation(1), Material.DIRT);
		List<Block> chestBlocks = new ArrayList<>();
		for (int i = 1; i < count; i++) {
			int randomInteger = RandomUtil.getRandomInteger(0, dirtBlocks.size() - 1);
			chestBlocks.add(dirtBlocks.get(randomInteger));
		}
		return new ChestManager(chestBlocks);
	}
	
	private void registerListener() {
		new BlockBreakListener(gameManager);
		new BlockPlaceListener(gameManager);
		new PlayerJoinListener(gameManager);
		new PlayerQuitListener(gameManager);	
		new PlayerMoveListener(gameManager);
		new PlayerItemDropPickupListener(gameManager);
		new PlayerInteractListener(gameManager);
		new PlayerDeathListener(gameManager);
		new PlayerDamageListener(gameManager);
		new EntityExplodeListener(gameManager);
		new PressurePlatePressListener(gameManager);
		new PlayerLoginListener(gameManager);
		new PlayerRespawnListener(gameManager);
		new InventoryClickListener(gameManager);
	}
	
	private void registerCommands() {
		new StatsCommand(gameManager);
	}
	
	public LocationManager getLocationManager() {
		return locationManager;
	}
	
	public ChestManager getChestManager() {
		return chestManager;
	}
		
	public class SetupException extends Exception {
		
		private static final long serialVersionUID = 1L;
		
		public SetupException() {}
		
		public SetupException(String message) { super(message); }
		
	}
	
}
