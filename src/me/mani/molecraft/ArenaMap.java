package me.mani.molecraft;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.ArenaMapPack.ArenaMapInfo;
import me.mani.molecraft.manager.SetupManager.SetupException;

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
	private List<String> displayLore;
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
	public ArenaMap(World world, String displayName, String builderName, List<String> displayLore, YamlConfiguration mapInfo, Location... cornerLocations) {
		this.world = world;
		this.displayName = displayName;
		this.builderName = builderName;
		this.displayLore = displayLore;
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
	 * Returns the name of the map, will be displayed ingame
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
	 * Returns the lore of the map, will be displayed ingame
	 * 
	 * @return The map's display lore
	 */
	public List<String> getDisplayLore() {
		return displayLore;
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
	
	/**
	 * Loads an map
	 * 
	 * @param mainWorld The world where the map is in
	 * @param mapInfoPath The path to the map info file 
	 * @return The arena map
	 * @throws SetupException If something doesn't work out
	 */
	public static ArenaMap loadArenaMap(World mainWorld, ArenaMapInfo arenaMapInfo) throws SetupException {
		File mapInfoFile = new File(mainWorld.getWorldFolder(), arenaMapInfo.getMapInfoPath());
		if (!mapInfoFile.exists())
			throw new SetupException("The map info file doesn't exists");
		YamlConfiguration mapInfo = YamlConfiguration.loadConfiguration(mapInfoFile);
		String displayName = arenaMapInfo.getDisplayName();
		String builderName = arenaMapInfo.getBuilderName();
		List<String> displayLore = Arrays.asList(arenaMapInfo.getDisplayLore().split(";"));
		List<String> cornerLocations = mapInfo.getStringList(Constants.CORNER_LOCATION);
		if (displayName.equals("") || builderName.equals("") || cornerLocations.isEmpty() || cornerLocations.size() < 2)
			throw new SetupException("The config isn't valid");
		Location cornerLocation1 = ConvertUtil.toLocation(cornerLocations.get(0), mainWorld);
		Location cornerLocation2 = ConvertUtil.toLocation(cornerLocations.get(1), mainWorld);
		if (cornerLocation1 == null || cornerLocation2 == null)
			throw new SetupException("One of the locations, or both, doesn't ecists");
		return new ArenaMap(mainWorld, displayName, builderName, displayLore, mapInfo, cornerLocation1, cornerLocation2);
	}
	
}
