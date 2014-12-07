package me.mani.molecraft.util;

import org.bukkit.Location;

public class WoolLocation {
	
	private Location loc;
	private byte data;
	
	public WoolLocation(Location loc, byte data) {
		this.loc = loc;
		this.data = data;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public byte getColor() {
		return data;
	}

}
