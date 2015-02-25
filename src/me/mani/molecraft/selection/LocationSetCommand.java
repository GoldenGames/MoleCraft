package me.mani.molecraft.selection;

import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.manager.TeamManager.Team;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

public class LocationSetCommand extends MoleCraftCommand {

	public LocationSetCommand() {
		super("setpoint", true);
	}

	@Override
	public String onCommand(Player p, String[] args) {
		if (args.length != 1)
			return "Zu wenige Argumente";
		
		if (!NumberUtils.isNumber(args[0]))
			return "Die Id muss ene Zahl sein";
		
		int id = Integer.valueOf(args[0]);
		
		if (!(0 <= id && id <= 7))
			return "Die Id muss zwischen 0 und 7 sein";
		
		// TODO: Add some tricky thing to save the location
		
		return "Location gesetzt! §7[" + Team.getById(id) + "] (" + id + ")";
	}

}
