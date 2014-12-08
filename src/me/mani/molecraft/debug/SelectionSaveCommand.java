package me.mani.molecraft.debug;

import me.mani.goldenapi.GoldenAPI;
import me.mani.goldenapi.mysql.ConvertUtil;
import me.mani.molecraft.debug.DebugManager.MessageType;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectionSaveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		SelectionTool tool = DebugManager.getSelectionTool();
		
		if (!tool.hasQualifiedLocations()) {
			DebugManager.send(p, MessageType.ERROR, "Setze erst die beiden Locations!");
			return true;
		}
		
		GoldenAPI.getManager().update("molecraft_arena", "id", 1, "world", tool.getWorld().getName());
		
		GoldenAPI.getManager().update("molecraft_arena", "id", 1, "loc1", ConvertUtil.toString(tool.getFirstLocation()));		
		GoldenAPI.getManager().update("molecraft_arena", "id", 1, "loc2", ConvertUtil.toString(tool.getSecondLocation()));
		
		DebugManager.send(p, MessageType.INFO, "Die Arena wurde gespeichert!");
		
		return true;
	}

}