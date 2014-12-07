package me.mani.molecraft.util;

import me.mani.molecraft.MoleCraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdvCommandExecuter implements CommandExecutor {

	public AdvCommandExecuter(String name) {
		getPlugin().getCommand(name).setExecutor(this);
	}
	
	public MoleCraft getPlugin() {
		return MoleCraft.getInstance();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}

}
