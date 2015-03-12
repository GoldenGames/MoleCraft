package me.mani.molecraft;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mani.molecraft.manager.SetupManager.SetupException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

public class ArenaMapPack {
	
	private List<ArenaMapInfo> arenaMapInfos;
	private YamlConfiguration mapPackInfo;
	
	public ArenaMapPack(List<ArenaMapInfo> arenaMapInfos, YamlConfiguration mapPackInfo) {
		this.arenaMapInfos = arenaMapInfos;
		this.mapPackInfo = mapPackInfo;
	}
		
	public boolean containsArenaMapInfo(int id) {
		return arenaMapInfos.size() - 1 >= id;
	}
	
	public ArenaMapInfo getArenaMapInfo(int id) {
		return arenaMapInfos.get(id);
	}
	
	public List<ArenaMapInfo> getArenaMapInfos() {
		return arenaMapInfos;
	}
	
	public YamlConfiguration getMapPackInfo() {
		return mapPackInfo;
	}
	
	@SuppressWarnings("unchecked")
	public static ArenaMapPack loadArenaMapPack(String mapPackInfoPath) throws SetupException {
		World world = Bukkit.getWorld("map");
		if (world == null)
			throw new SetupException("The world loaded, doesn't exists");
		File mapPackInfoFile = new File(world.getWorldFolder(), "mapPackInfo.yml");
		if (!mapPackInfoFile.exists())
			throw new SetupException("The map pack info file doesn't exists");
		YamlConfiguration mapPackInfo = YamlConfiguration.loadConfiguration(mapPackInfoFile);
		return new ArenaMapPack((List<ArenaMapInfo>) mapPackInfo.getList("arenaMaps"), mapPackInfo);
	}
	
	@SerializableAs ("ArenaMapInfo")
	public static class ArenaMapInfo implements ConfigurationSerializable {
		
		private String displayName;
		private String displayLore;
		private String mapInfoPath;
		
		public ArenaMapInfo(String displayName, String displayLore, String mapInfoPath) {
			this.displayName = displayName;
			this.displayLore = displayLore;
			this.mapInfoPath = mapInfoPath;
		}
		
		public ArenaMapInfo(Map<String, Object> map) {
			displayName = map.get("displayName").toString();
			displayLore = map.get("displayLore").toString();
			mapInfoPath = map.get("mapInfoPath").toString();
		}

		@Override
		public Map<String, Object> serialize() {
			return new HashMap<String, Object>() { private static final long serialVersionUID = 1L; {
				put("displayName", displayName);
				put("displayLore", displayLore);
				put("mapInfoPath", mapInfoPath);
			}};
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getDisplayLore() {
			return displayLore;
		}

		public String getMapInfoPath() {
			return mapInfoPath;
		}
		
	}

}
