package me.mani.molecraft.debug;

import me.mani.molecraft.debug.DebugManager.MessageType;
import me.mani.molecraft.game.LocationManager;
import me.mani.molecraft.game.TeamManager.Team;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationSetCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		if (args.length != 1)
			return true;
		
		if (!NumberUtils.isNumber(args[0])) {
			DebugManager.send(p, MessageType.ERROR, "Die ID muss eine Zahl sein!");
			return true;
		}
		
		int id = Integer.valueOf(args[0]);
		
		if (id > 8 || id < 1) {
			DebugManager.send(p, MessageType.ERROR, "Die ID muss zwischen 1 und 8 sein!");
			return true;
		}
		
		LocationManager.add(id, p.getLocation());
		DebugManager.send(p, MessageType.INFO, "Location gesetzt! §7[" + Team.getById(id) + "] (" + id + ")");
		
		return true;
	}


}
