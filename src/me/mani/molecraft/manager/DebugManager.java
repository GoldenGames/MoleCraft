package me.mani.molecraft.manager;

import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.selection.LocationSetCommand;
import me.mani.molecraft.selection.SelectionListener;
import me.mani.molecraft.selection.SelectionSaveCommand;
import me.mani.molecraft.selection.SelectionTool;
import me.mani.molecraft.selection.WorldCommand;

public class DebugManager implements MainManager {
	
	private static SelectionTool tool;

	private MoleCraft moleCraft;
	
	public DebugManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
		tool = new SelectionTool();
		new SelectionListener(this, tool);
		new SelectionSaveCommand(this);
		new LocationSetCommand(this);
		new WorldCommand(this);
	}
	
	public static SelectionTool getSelectionTool() {
		return tool;
	}

	@Override
	public MoleCraft getMoleCraft() {
		return moleCraft;
	}
}
