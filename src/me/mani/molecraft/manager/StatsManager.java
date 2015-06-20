package me.mani.molecraft.manager;

import java.util.UUID;
import java.util.function.Consumer;


import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.util.PlayerStats;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;


public class StatsManager {

	private static final String STATS = "stats";
	
	private DatabaseManager sql;
	
	public StatsManager(DatabaseManager sql) {
		this.sql = sql;
	}
	
	public void setupStatsBoard() {
		for (int i = 1; i <= 5; i++) {
			final int i2 = i;
			String raw = (String) sql.get("molecraft", "key", STATS + i, "value");
			if (raw == null)
				return;
			Location loc = ConvertUtil.toLocation(String.valueOf(raw), Bukkit.getWorld("world"));
			if (loc == null || loc.getBlock().getType() != Material.WALL_SIGN)
				return;
			PlayerStats stats = getPlayerStats(i2);
			if (stats == null)
				return;
			Sign sign = (Sign) loc.getBlock().getState();
			sign.setLine(0, "§7- §8" + i2 + ". §7-");
			sign.setLine(1, "");
			sign.setLine(2, stats.getLastName());
			sign.setLine(3, "§c" + stats.getPoints());
			sign.update();
			loc.add(0, 1, 0);
			if (loc.getBlock().getType() != Material.SKULL)
				return;
			Skull skull = (Skull) loc.getBlock().getState();
			skull.setOwner(stats.getLastName());
			skull.update();
		}
	}
	
	public PlayerStats getPlayerStats(int rank) {
		String raw = ((String) sql.get(PlayerStats.TABLE, PlayerStats.POINTS_ALIAS, rank, true, PlayerStats.UUID_ALIAS));
		try {
			if (raw == null || UUID.fromString(raw) == null)
				return null;
			return PlayerStats.getPlayerStats(UUID.fromString(raw));
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();	
		}	
		return null;
	}
	
	public void getPlayerStats(int rank, Consumer<PlayerStats> consumer) {
		String raw = ((String) sql.get(PlayerStats.TABLE, PlayerStats.POINTS_ALIAS, rank, true, PlayerStats.UUID_ALIAS));
		try {
			if (raw == null || UUID.fromString(raw) == null)
				consumer.accept(null);
			PlayerStats.getPlayerStats(UUID.fromString(raw), (stats) -> consumer.accept(stats));
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();	
		}	
		consumer.accept(null);	
	}

	public void sendAll() {
		for (PlayerStats stats : PlayerStats.getPlayerStats())
			stats.sendData();
	}
	
}