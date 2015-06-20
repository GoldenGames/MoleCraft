package me.mani.molecraft.manager;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.molecraft.ArenaMapPack;
import me.mani.molecraft.Constants;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.commands.StartCommand;
import me.mani.molecraft.commands.StatsCommand;
import me.mani.molecraft.listener.AsyncPlayerChatListener;
import me.mani.molecraft.listener.BlockBreakListener;
import me.mani.molecraft.listener.BlockPlaceListener;
import me.mani.molecraft.listener.EntityDamageByEntityListener;
import me.mani.molecraft.listener.EntityExplodeListener;
import me.mani.molecraft.listener.InventoryClickListener;
import me.mani.molecraft.listener.InventoryCloseListener;
import me.mani.molecraft.listener.InventoryOpenListener;
import me.mani.molecraft.listener.ItemSpawnListener;
import me.mani.molecraft.listener.PlayerChatTabCompleteListener;
import me.mani.molecraft.listener.PlayerDamageListener;
import me.mani.molecraft.listener.PlayerDeathListener;
import me.mani.molecraft.listener.PlayerInteractEntityListener;
import me.mani.molecraft.listener.PlayerInteractListener;
import me.mani.molecraft.listener.PlayerItemDropPickupListener;
import me.mani.molecraft.listener.PlayerJoinListener;
import me.mani.molecraft.listener.PlayerLoginListener;
import me.mani.molecraft.listener.PlayerMoveListener;
import me.mani.molecraft.listener.PlayerQuitListener;
import me.mani.molecraft.listener.PlayerRespawnListener;
import me.mani.molecraft.listener.PlayerToggleSneakListener;
import me.mani.molecraft.listener.PressurePlatePressListener;
import me.mani.molecraft.listener.WeatherChangeListener;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;

public class SetupManager {
	
	public MoleCraft moleCraft;
	private GameManager gameManager;
	
	private DatabaseManager sql;
	private ArenaMapManager arenaMapManager;
	private LocationManager locationManager;
	
	public SetupManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
		this.gameManager = moleCraft.gameManager;
	}
	
	public boolean setup() {
		try {
			arenaMapManager = loadMapPack();
			System.out.println("Loaded Map Pack");
			sql = loadSQL();
			System.out.println("Loaded MySQL");
			locationManager = loadLocationManager();
			System.out.println("Loaded Locations");
			loadChunks();
			System.out.println("Loaded Chunks");
			registerListener();
			registerCommands();
			System.out.println("Registered listeners and commands");
		}
		catch (SetupException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private ArenaMapManager loadMapPack() throws SetupException {
		return new ArenaMapManager(moleCraft, ArenaMapPack.loadArenaMapPack("mapPackInfo"));
	}
	
	private DatabaseManager loadSQL() throws SetupException {
		DatabaseManager sql = GoldenAPI.connectToSQL(moleCraft);
		if (!sql.isAvaible(Constants.MAIN_TABLE, "key", Constants.CONNECTION_TEST))
			throw new SetupException("The MySQL database ist not avaible");
		return sql;	
	}
	
	private LocationManager loadLocationManager() throws SetupException {
		World lobbyWorld = Bukkit.getWorld("world");
		lobbyWorld.setDifficulty(Difficulty.PEACEFUL);
		Location lobbySpawnLocation = 
				ConvertUtil.toLocation((String) sql.get(Constants.MAIN_TABLE, "key", Constants.LOBBY_SPAWN, "value"), lobbyWorld);
		Location parkourSpawnLocation =
				ConvertUtil.toLocation((String) sql.get(Constants.MAIN_TABLE, "key", Constants.PARKOUR_SPAWN, "value"), lobbyWorld);		
		Location statsDisplayLocation =
				ConvertUtil.toLocation((String) sql.get(Constants.MAIN_TABLE, "key", Constants.STATS_DISPLAY, "value"), lobbyWorld);
		return new LocationManager(lobbyWorld, lobbySpawnLocation, parkourSpawnLocation, statsDisplayLocation);	
	}
	
	private void loadChunks() {
		Chunk spawnChunk = locationManager.LOBBY_SPAWN.getChunk();
		if (!spawnChunk.isLoaded())
			spawnChunk.load();
	}
	
	private void registerListener() {
		new AsyncPlayerChatListener(gameManager);
		new BlockBreakListener(gameManager);
		new BlockPlaceListener(gameManager);
		new EntityExplodeListener(gameManager);
		new ItemSpawnListener(gameManager);
		new PlayerJoinListener(gameManager);
		new PlayerQuitListener(gameManager);	
		new PlayerMoveListener(gameManager);
		new PlayerItemDropPickupListener(gameManager);
		new PlayerInteractListener(gameManager);
		new PlayerDeathListener(gameManager);
		new PlayerDamageListener(gameManager);
		new PressurePlatePressListener(gameManager);
		new PlayerLoginListener(gameManager);
		new PlayerRespawnListener(gameManager);
		new InventoryClickListener(gameManager);
		new WeatherChangeListener(gameManager);
		new PlayerInteractEntityListener(gameManager);
		new InventoryOpenListener(gameManager);
		new EntityDamageByEntityListener(gameManager);
		new PlayerToggleSneakListener(gameManager);
		new InventoryCloseListener(gameManager);
		new PlayerChatTabCompleteListener(gameManager);
	}
	
	private void registerCommands() {
		new StatsCommand(gameManager);
		new StartCommand(gameManager);
	}
	
	public DatabaseManager getSQL() {
		return sql;
	}
	
	public ArenaMapManager getArenaMapManager() {
		return arenaMapManager;
	}
	
	public LocationManager getLocationManager() {
		return locationManager;
	}
	
	public static class SetupException extends Exception {
		
		private static final long serialVersionUID = 1L;
		
		public SetupException() {}
		
		public SetupException(String message) { super(message); }
		
	}
	
}
