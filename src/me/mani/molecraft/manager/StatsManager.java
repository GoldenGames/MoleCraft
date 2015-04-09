package me.mani.molecraft.manager;

import java.util.HashMap;
import java.util.UUID;

import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.molecraft.util.PlayerStats;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

public class StatsManager {

	private static final String STATS_TEMPLATE = "stats";
	
	private DatabaseManager sql;
	private HashMap<Player, PlayerStats> playerStats = new HashMap<>();
	
	public StatsManager(DatabaseManager sql) {
		this.sql = sql;
	}
	
	public void setupStatsBoard() {
		for (int i = 1; i <= 5; i++) {
			Location loc = ConvertUtil.toLocation((String) sql.get("molecraft", "key", STATS_TEMPLATE + i, "value"), Bukkit.getWorld("world"));
			if (loc == null || loc.getBlock().getType() != Material.WALL_SIGN || getPlayerStats(i) == null)
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
	
	public void addPlayerStats(Player p) {
		PlayerStats stats = new PlayerStats(p);
		stats.sendData();
		playerStats.put(p, stats);
	}
	
	public PlayerStats getPlayerStats(Player p) {
		return playerStats.get(p);
	}
	
	public PlayerStats getPlayerStats(UUID uuid) {
		for (PlayerStats stats : playerStats.values())
			if (stats.getUUID().equals(uuid))
				return stats;
		return null;
	}
	
	public PlayerStats getPlayerStats(int rank) {
		String raw = ((String) sql.get(PlayerStats.TABLE, PlayerStats.WINS_ALIAS, rank, true, PlayerStats.UUID_ALIAS));
		try {
			if (raw == null || UUID.fromString(raw) == null)
				return null;
			return new PlayerStats(UUID.fromString(raw));
		}
		catch (IllegalArgumentException e) {}
		return null;
	}

	public void sendAll() {
		for (PlayerStats stats : playerStats.values())
			stats.sendData();
	}
}