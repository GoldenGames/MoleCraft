package me.mani.molecraft.listener;

import java.util.UUID;

import org.bukkit.entity.Player;

public class StatsEvent {
	
	private UUID uuid;
	private StatsEventType type;
	private int value;
	
	public StatsEvent(Player p, StatsEventType type, int value) {
		this(p.getUniqueId(), type, value);
	}
	
	public StatsEvent(UUID uuid, StatsEventType type, int value) {
		this.uuid = uuid;
		this.type = type;
		this.value = value;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public StatsEventType getType() {
		return this.type;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public enum StatsEventType {
		KILL ("Kill", 20), 
		DEATH ("Tod", 0), 
		WIN ("Gewonnenes Spiel", 50),
		GAME ("Gespieltes Spiel", 0),
		CHEST ("Geöffnete Kisten", 5);
		
		private String displayName;
		private int points;
		
		private StatsEventType(String displayName, int points) {
			this.displayName = displayName;
			this.points = points;
		}
		
		public String getDisplayName() {
			return displayName;
		}
		
		public int getPoints() {
			return points;
		}
	}

}
