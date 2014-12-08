package me.mani.molecraft.game;

import java.util.HashMap;
import java.util.Map.Entry;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.Manager;
import me.mani.molecraft.MoleCraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationManager extends Manager {
	
	private static final String TABLE = "molecraft_arena";
	private static final String ID_ALIAS = "id";
	private static final String WORLD_ALIAS = "world";
	
	private static final String LOBBY_SPAWN_ALIAS = "lobbySpawn";
	private static final String PARKOUR_SPAWN_ALIAS = "parkourSpawn";
	private static final String STATS_DISPLAY_ALIAS = "statsDisplay";
	private static final String SPAWN_ALIAS = "spawn#";
	
	private int arenaId;
	
	private static World world;
	
	private static Location lobbySpawn;
	private static Location parkourSpawn;
	private static Location statsDisplay;
		
	private static HashMap<Integer, Location> allLocations = new HashMap<>();
	
	public LocationManager(int arenaId) {
		this.arenaId = arenaId;
		world = Bukkit.getWorld((String) GoldenAPI.getManager().get(TABLE, ID_ALIAS, arenaId, WORLD_ALIAS));
	}
	
	public void loadAll() {
		
		// Lobby Spawn
		
		lobbySpawn = ConvertUtil.toLocation((String) GoldenAPI.getManager().get(MoleCraft.TABLE, MoleCraft.SETTING_ALIAS, LOBBY_SPAWN_ALIAS, MoleCraft.VALUE_ALIAS), world).add(0.5, 0, 0.5);
		
		// Parkour Spawn
		
		parkourSpawn = ConvertUtil.toLocation((String) GoldenAPI.getManager().get(MoleCraft.TABLE, MoleCraft.SETTING_ALIAS, PARKOUR_SPAWN_ALIAS, MoleCraft.VALUE_ALIAS), world).add(0.5, 0, 0.5);
		
		// Stats Location
		
		statsDisplay = ConvertUtil.toLocation((String) GoldenAPI.getManager().get(MoleCraft.TABLE, MoleCraft.SETTING_ALIAS, STATS_DISPLAY_ALIAS, MoleCraft.VALUE_ALIAS), world).add(0.5, 0, 0.5);
		
		// Arena Spawns
		
		for (int i = 1; i < 8; i++)
			allLocations.put(i, ConvertUtil.toLocation((String) GoldenAPI.getManager().get(TABLE, ID_ALIAS, arenaId, SPAWN_ALIAS.replace("#", "" + i)), world).add(0.5, 0, 0.5));
	}
	
	public void saveAll() {
		for (Entry<Integer, Location> entry : allLocations.entrySet())
			GoldenAPI.getManager().update(TABLE, ID_ALIAS, arenaId, SPAWN_ALIAS.replace("#", "" + entry.getKey()), ConvertUtil.toString(entry.getValue()));
	}

	public static void add(Integer id, Location loc) {
		if (id <= 8 && id >= 1)
			allLocations.put(id, loc);
	}

	public int getArenaId() {
		return this.arenaId;
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static Location getSpawn(int id) {
		if (id < 1 || id > 8)
			return null;
		return allLocations.get(id);
	}
	
	public static class SpecialLocation {
		public static Location LOBBY_SPAWN = lobbySpawn; 
		public static Location PARKOUR_SPAWN = parkourSpawn; 
		public static Location STATS_DISPLAY = statsDisplay;
	}
}
