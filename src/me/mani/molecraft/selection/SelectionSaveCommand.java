package me.mani.molecraft.selection;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectionSaveCommand extends MoleCraftCommand {

	public SelectionSaveCommand(MainManager mainManager) {
		super("save", mainManager);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		SelectionTool tool = DebugManager.getSelectionTool();
		
		if (!tool.hasQualifiedLocations()) {
			Messenger.send(p, "Setze erst die beiden Locations!");
			return true;
		}
		
		GoldenAPI.getManager().update("molecraft_arena", "id", 1, "world", tool.getWorld().getName());
		
		GoldenAPI.getManager().update("molecraft_arena", "id", 1, "loc1", ConvertUtil.toString(tool.getFirstLocation()));		
		GoldenAPI.getManager().update("molecraft_arena", "id", 1, "loc2", ConvertUtil.toString(tool.getSecondLocation()));
		
		Messenger.send(p, "Die Arena wurde gespeichert!");
		
		return true;
	}

	@Override
	public String onCommand(Player p, String[] args) {
		SelectionTool tool = DebugManager.getSelectionTool();
	
		if (!tool.hasQualifiedLocations())
			return "Setze erst beide Locations";
		
		// TODO: Add something that saves the arena
		
		return "Die Arena wurde gespeichert";
	}

}
