package me.mani.molecraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * A simple information holder class for a molecraft map
 * 
 * @author Overload
 */
public class ArenaMap {
	
	private World world;
	private String displayName;
	private String builderName;
	private Location[] cornerLocations = new Location[2]; 
	private YamlConfiguration mapInfo;
	
	private static ArenaMap currentMap;
	
	/**
	 * Set all the field and put this as current map
	 * 
	 * @param world The map's world
	 * @param displayName The map's display name
	 * @param builderName The map's builder's name
	 * @param mapInfo The map's configuration
	 * @param cornerLocations The map's corner locations
	 */
	public ArenaMap(World world, String displayName, String builderName, YamlConfiguration mapInfo, Location... cornerLocations) {
		this.world = world;
		this.displayName = displayName;
		this.builderName = builderName;
		this.cornerLocations = cornerLocations;
		this.mapInfo = mapInfo;
		currentMap = this;
	}

	/**
	 * Returns the world from this map, which name should be 'map'
	 * 
	 * @return The map's world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Returns the name of the map, which will be displayed ingame
	 * 
	 * @return The map's display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Returns the builder's name of the map, will be displayed ingame
	 * 
	 * @return The map's builder's name
	 */
	public String getBuilderName() {
		return builderName;
	}
	
	/**
	 * Returns the corner location of the map with the specified identifier
	 * 
	 * @param id The id of the corner location, must be between 0 and 1
	 * @return The specified corner location, or null
	 */
	public Location getCornerLocation(int id) {
		if (0 <= id && id <= 1)
			return cornerLocations[id];
		return null;
	}

	/**
	 * Returns the configuration of this map
	 * 
	 * @return The map's configuration
	 */
	public YamlConfiguration getMapInfo() {
		return mapInfo;
	}
	
	/**
	 * Returns the game's current map
	 * 
	 * @return The game's current map
	 */
	public static ArenaMap getCurrentMap() {
		return currentMap;
	}
	
}
