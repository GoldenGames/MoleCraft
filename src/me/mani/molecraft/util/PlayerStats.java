package me.mani.molecraft.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.molecraft.MoleCraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class PlayerStats {
	
	public static final String TABLE = "molecraft_player";
	
	public static final String UUID_ALIAS = "uuid";
	public static final String LAST_NAME_ALIAS = "lastName";
	public static final String POINTS_ALIAS = "points";
	public static final String KILLS_ALIAS = "kills";
	public static final String DEATHS_ALIAS = "deaths";
	public static final String WINS_ALIAS = "wins";
	public static final String GAMES_ALIAS = "games";
	public static final String CHESTS_ALIAS = "chests";
	
	private static DatabaseManager manager;
	private static ExecutorService executor;
	
	private static Map<String, PlayerStats> playerStats = new HashMap<>();
	
	private UUID uuid;
	private String lastName;
	private int ranking;
	private int points;
	private int kills;
	private int deaths;
	private int wins;
	private int games;
	private int chests;
	private OfflinePlayerStats offlinePlayerStats;
	
	public PlayerStats(Player player) {
		this.uuid = player.getUniqueId();
		this.lastName = player.getName();
		offlinePlayerStats = new OfflinePlayerStats();
		fetchData();
	}
	
	public PlayerStats(UUID uuid) {
		this.uuid = uuid;
		this.lastName = UUIDHandler.getPlayerName(uuid);
		fetchData();
	}
	
	public PlayerStats(Player player, Consumer<PlayerStats> consumer) {
		this.uuid = player.getUniqueId();
		this.lastName = player.getName();
		offlinePlayerStats = new OfflinePlayerStats();
		executor.execute(() -> {
			fetchData();
			Bukkit.getScheduler().runTask(MoleCraft.getInstance(), () -> consumer.accept(this));
		});
	}
	
	public PlayerStats(UUID uuid, Consumer<PlayerStats> consumer) {
		this.uuid = uuid;
		UUIDHandler.getPlayerName(uuid, (lastName) -> {
			this.lastName = lastName;
			fetchData();
			Bukkit.getScheduler().runTask(MoleCraft.getInstance(), () -> consumer.accept(this));
		});
	}
	
	public void fetchData() {
		if (!manager.isAvaible(TABLE, UUID_ALIAS, uuid.toString()))
			manager.insert(TABLE, UUID_ALIAS, uuid.toString());
		try {
			ResultSet resultSet = manager.getConnection().getMySQL().query(
				"SELECT COUNT(*) FROM " + TABLE + " WHERE " + POINTS_ALIAS + " >= (SELECT " + POINTS_ALIAS + " FROM " + TABLE + " WHERE " + UUID_ALIAS + "='" + uuid + "')"
			);
			resultSet.last();
			if (resultSet.getRow() != 0)
				resultSet.last();
			this.ranking = resultSet.getInt(1);
		} catch (SQLException e) {
			this.ranking = 0;
			e.printStackTrace();
		}
		this.points = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), POINTS_ALIAS));
		this.kills = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), KILLS_ALIAS));
		this.deaths = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), DEATHS_ALIAS));
		this.wins = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), WINS_ALIAS));
		this.games = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), GAMES_ALIAS));
		this.chests = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), CHESTS_ALIAS));
	}
	
	public void sendData() {
		manager.update(TABLE, UUID_ALIAS, uuid.toString(), POINTS_ALIAS, points);
		manager.update(TABLE, UUID_ALIAS, uuid.toString(), KILLS_ALIAS, kills);
		manager.update(TABLE, UUID_ALIAS, uuid.toString(), DEATHS_ALIAS, deaths);
		manager.update(TABLE, UUID_ALIAS, uuid.toString(), WINS_ALIAS, wins);
		manager.update(TABLE, UUID_ALIAS, uuid.toString(), GAMES_ALIAS, games);
		manager.update(TABLE, UUID_ALIAS, uuid.toString(), CHESTS_ALIAS, chests);
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getKills() {
		return kills;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public int getGames() {
		return games;
	}
	
	public void setGames(int games) {
		this.games = games;
	}
	
	public int getChests() {
		return chests;
	}
	
	public void setChests(int chests) {
		this.chests = chests;
	}

	public boolean hasOfflinePlayerStats() {
		return offlinePlayerStats != null;
	}
	
	public OfflinePlayerStats getOfflinePlayerStats() {
		return offlinePlayerStats;
	}
	
	public static Collection<PlayerStats> getPlayerStats() {
		return playerStats.values();
	}
	
	public static boolean hasPlayerStats(Player player) {
		return hasPlayerStats(player.getUniqueId());
	}
	
	public static boolean hasPlayerStats(UUID uuid) {
		return manager.isAvaible(TABLE, UUID_ALIAS, uuid.toString());
	}
	
	public static PlayerStats getPlayerStats(Player player) {
		if (playerStats.containsKey(player.getUniqueId().toString()))
			return playerStats.get(player.getUniqueId().toString());
		else
			return new PlayerStats(player);
	}
	
	public static void getPlayerStats(Player player, Consumer<PlayerStats> consumer) {
		consumer.accept(getPlayerStats(player));
	}
	
	public static PlayerStats getPlayerStats(UUID uuid) {
		if (playerStats.containsKey(uuid.toString()))
			return playerStats.get(uuid.toString());
		else
			return new PlayerStats(uuid);
	}
	
	public static void getPlayerStats(UUID uuid, Consumer<PlayerStats> consumer) {
		consumer.accept(getPlayerStats(uuid));
	}
	
	public static void addDatabaseManager(DatabaseManager databaseManager) {
		manager = databaseManager;
		executor = Executors.newCachedThreadPool();
	}
	
}
