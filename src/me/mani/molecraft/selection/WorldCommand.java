package me.mani.molecraft.selection;

import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldCommand extends MoleCraftCommand {

	public WorldCommand(MainManager mainManager) {
		super("world", mainManager);
	}

	@Override
	public String onCommand(Player p, String[] args) {
		
		if (args.length != 1)
			return "Du musst eine Welt angeben";
		
		World world = Bukkit.getWorld(args[0]);
		
		if (world == null)
			return "Diese Welt gibt es nicht";
		
		p.teleport(world.getSpawnLocation());
		
		return "Du bist nun in der Welt '" + world.getName() + "'";
	}

}
