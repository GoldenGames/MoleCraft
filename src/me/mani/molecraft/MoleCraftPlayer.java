package me.mani.molecraft;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MoleCraftPlayer {
	
	private static HashMap<Player, MoleCraftPlayer> moleCraftPlayers = new HashMap<>();
	
	private Player player;
	private Location deathLocation;
	private boolean isIngame;
	private boolean isSpectator;
	
	public MoleCraftPlayer(Player player) {
		moleCraftPlayers.put(player, this);
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getDeathLocation() {
		return deathLocation;
	}
	
	public void setDeathLocation(Location location) {
		deathLocation = location;
	}
	
	public boolean isIngame() {
		return isIngame;
	}
	
	public void setIngame(boolean ingame) {
		isIngame = ingame;
	}
	
	public boolean isSpectator() {
		return isSpectator;
	}
	
	public void setSpectator(boolean spectator) {
		isSpectator = spectator;
	}
	
	public static MoleCraftPlayer getMoleCraftPlayer(Player player) {
		return moleCraftPlayers.get(player);
	}
	
	public static void removeMoleCraftPlayer(MoleCraftPlayer moleCraftPlayer) {
		moleCraftPlayers.remove(moleCraftPlayer.getPlayer());
	}
	
	public static Collection<MoleCraftPlayer> getMoleCraftPlayers() {
		return moleCraftPlayers.values();
	}

}
