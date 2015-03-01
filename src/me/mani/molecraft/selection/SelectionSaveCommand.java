package me.mani.molecraft.selection;

import java.util.Arrays;

import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.entity.Player;

public class SelectionSaveCommand extends MoleCraftCommand {

	public SelectionSaveCommand(MainManager mainManager) {
		super("save", mainManager);
	}

	@Override
	public String onCommand(Player p, String[] args) {
		SelectionTool tool = DebugManager.getSelectionTool();
	
		if (!tool.hasQualifiedLocations())
			return "Setze erst beide Locations";
		
		debugManager.getMoleCraft().getConfig().set("cornerLocations", Arrays.asList(
			ConvertUtil.toString(tool.getFirstLocation()),
			ConvertUtil.toString(tool.getSecondLocation())
		));
		debugManager.getMoleCraft().saveConfig();
		
		return "Die Arena wurde gespeichert";
	}

}
