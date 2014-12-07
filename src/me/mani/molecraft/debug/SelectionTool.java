package me.mani.molecraft.debug;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class SelectionTool {
	
	private static final Material SELECTION_ITEM = Material.STICK;
	
	private Location loc1;
	private Location loc2;
	private Selection currentSelection;

	public void setFirstLocation(Location loc) {
		this.loc1 = loc;
		updateSelection();
	}
	
	public void setSecondLocation(Location loc) {
		this.loc2 = loc;
		updateSelection();
	}
	
	public Location getFirstLocation() {
		return loc1;
	}
	
	public Location getSecondLocation() {
		return loc2;
	}
	
	private void updateSelection() {
		if (hasQualifiedLocations())
			currentSelection = new Selection(loc1, loc2);
	}
	
	public boolean hasQualifiedLocations() {
		return loc1 != null && loc2 != null;
	}
	
	public Selection getSelection() {
		return currentSelection;
	}
	
	public World getWorld() {
		return loc1.getWorld();
	}
	
	public Material getSelectionItem() {
		return SELECTION_ITEM;
	}
	
	public int getCount() {
		if (currentSelection == null)
			return 0;
		return currentSelection.getCount();
	}
	
}
