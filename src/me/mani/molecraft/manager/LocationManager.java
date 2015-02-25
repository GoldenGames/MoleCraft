package me.mani.molecraft.manager;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationManager {
			
	private World world;
	
	public final Location LOBBY_SPAWN;
	public final Location PARKOUR_SPAWN;
	public final Location STATS_DISPLAY;
		
	private Map<Integer, Location> spawnLocations;
	
	public LocationManager(World world, Location lobbySpawnLocation, Location parkourSpawnLocation, Location statsDisplayLocation, Map<Integer, Location> spawnLocations) {
		this.world = world;
		LOBBY_SPAWN = lobbySpawnLocation;
		PARKOUR_SPAWN = parkourSpawnLocation;
		STATS_DISPLAY = statsDisplayLocation;
		this.spawnLocations = spawnLocations;
	}

	public Location getSpawnLocation(int id) {
		if (0 <= id && id <= 7)
			return spawnLocations.get(id);
		return null;
	}
	
	public World getWorld() {
		return world;
	}

}
