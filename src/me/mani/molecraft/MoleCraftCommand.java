package me.mani.molecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MoleCraftCommand implements CommandExecutor {
	
	private String label;
	private boolean playerOnly;
	
	public MoleCraftCommand(String label) {
		this(label, false);
	}
	
	public MoleCraftCommand(String label, boolean playerOnly) {
		this.label = label;
		this.playerOnly = playerOnly;
		register();
	}
	
	private void register() {
		MoleCraft.getInstance().getCommand(label).setExecutor(this);
	}
	
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (playerOnly && !(sender instanceof Player))
			Messenger.sendAll(onCommand(null, args));
		else
			Messenger.send((Player) sender, (onCommand((Player) sender, args)));		
		return true;
	}
	
	public abstract String onCommand(Player p, String[] args);

}
