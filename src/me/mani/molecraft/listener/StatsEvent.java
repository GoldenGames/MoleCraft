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
		KILL, DEATH, WIN, GAME, CHEST;
	}

}
