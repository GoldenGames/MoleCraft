package me.mani.molecraft.util;

import java.util.UUID;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.DatabaseManager;
import me.mani.goldenapi.util.PlayerNameUtil;

import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class PlayerStats {
	
	public static final String TABLE = "molecraft_player";
	
	public static final String UUID_ALIAS = "uuid";
	public static final String LAST_NAME_ALIAS = "lastName";
	public static final String KILLS_ALIAS = "kills";
	public static final String DEATHS_ALIAS = "deaths";
	public static final String WINS_ALIAS = "wins";
	public static final String GAMES_ALIAS = "games";
	public static final String CHESTS_ALIAS = "chests";
	
	private DatabaseManager manager;
	
	private UUID uuid;
	private String lastName;
	private int kills;
	private int deaths;
	private int wins;
	private int games;
	private int chests;
	
	public PlayerStats(Player p) {
		this.manager = GoldenAPI.getManager();
		this.uuid = p.getUniqueId();
		this.lastName = p.getName();
		fetchData();
	}
	
	public PlayerStats(UUID uuid) {
		this.manager = GoldenAPI.getManager();
		this.uuid = uuid;
		this.lastName = PlayerNameUtil.fromUUID(uuid);
		fetchData();
	}
	
	public void fetchData() {
		if (!manager.isAvaible(TABLE, UUID_ALIAS, uuid.toString())) {
			manager.insert(TABLE, UUID_ALIAS, uuid.toString());
			sendData(0, 0, 0, 0, 0);
		}
		this.kills = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), KILLS_ALIAS));
		this.deaths = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), DEATHS_ALIAS));
		this.wins = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), WINS_ALIAS));
		this.games = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), GAMES_ALIAS));
		this.chests = NumberConversions.toInt(manager.get(TABLE, UUID_ALIAS, uuid.toString(), CHESTS_ALIAS));
	}
	
	public void sendData() {
		sendData(kills, deaths, wins, games, chests);
	}
	
	private void sendData(int kills, int deaths, int wins, int games, int chests) {
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
		return this.lastName;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public void setGames(int games) {
		this.games = games;
	}
	
	public void setChests(int chests) {
		this.chests = chests;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getGames() {
		return games;
	}
	
	public int getChests() {
		return chests;
	}
	
}
