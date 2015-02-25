package me.mani.molecraft.selection;

import java.util.ArrayList;
import java.util.List;

import me.mani.molecraft.util.BlockMath;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Selection {
	
	private Location loc1;
	private Location loc2;
	
	private List<Block> allBlocks = new ArrayList<>();
	
	public Selection(Location loc1, Location loc2) {
		this.loc1 = loc1;
		this.loc2 = loc2;
	}
	
	public void calculateBlocks() {
		allBlocks = BlockMath.getBlocks(loc1, loc2);
	}
	
	public List<Block> getBlocks() {
		return allBlocks;
	}

	public int getCount() {
		return BlockMath.countBlocks(loc1, loc2);
	}
}
