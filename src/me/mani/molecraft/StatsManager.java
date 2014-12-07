package me.mani.molecraft;

import java.util.HashMap;
import java.util.UUID;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.molecraft.setup.SetupManager;
import me.mani.molecraft.util.PlayerStats;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

public class StatsManager extends Manager {
	
	private static DatabaseManager manager;
	private static final String STATS_TEMPLATE = "stats";
	
	private static HashMap<Player, PlayerStats> allPlayerStats = new HashMap<>();
	
	public StatsManager() {
		manager = GoldenAPI.getManager();
	}
	
	public void setupStatsBoard() {
		for (int i = 1; i < 5; i++) {
			Location loc = ConvertUtil.toLocation((String) manager.get("molecraft", "setting", STATS_TEMPLATE + i, "value"), SetupManager.getWorld());
			if (loc.getBlock().getType() != Material.WALL_SIGN || getPlayerStats(i) == null)
				continue;
			Sign sign = (Sign) loc.getBlock().getState();
			PlayerStats stats = getPlayerStats(i);			
			sign.setLine(0, "§7- §8" + i + ". §7-");
			sign.setLine(1, "");
			sign.setLine(2, stats.getLastName());
			sign.setLine(3, "§c" + stats.getWins());
			sign.update();
			loc.add(0, 1, 0);
			if (loc.getBlock().getType() != Material.SKULL)
				continue;
			Skull skull = (Skull) loc.getBlock().getState();
			skull.setOwner(stats.getLastName());
			skull.update();
		}
	}
	
	public static void addPlayerStats(Player p) {
		PlayerStats stats = new PlayerStats(p);
		stats.sendData();
		allPlayerStats.put(p, stats);
	}
	
	public static PlayerStats getPlayerStats(Player p) {
		return allPlayerStats.get(p);
	}
	
	public static PlayerStats getPlayerStats(UUID uuid) {
		for (PlayerStats stats : allPlayerStats.values())
			if (stats.getUUID().equals(uuid))
				return stats;
		return null;
	}
	
	public static PlayerStats getPlayerStats(int rank) {
		String raw = ((String) manager.get(PlayerStats.TABLE, PlayerStats.WINS_ALIAS, rank, true, PlayerStats.UUID_ALIAS));
		try {
			if (raw == null || UUID.fromString(raw) == null)
				return null;
			return new PlayerStats(UUID.fromString(raw));
		}
		catch (IllegalArgumentException e) {}
		return null;
	}

	public void sendAll() {
		for (PlayerStats stats : allPlayerStats.values())
			stats.sendData();
	}
}