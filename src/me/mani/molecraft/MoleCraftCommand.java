package me.mani.molecraft;

import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MoleCraftCommand implements CommandExecutor {
	
	protected GameManager gameManager;
	protected DebugManager debugManager;
	private String label;
	
	public MoleCraftCommand(String label, MainManager mainManager) {
		this.label = label;
		if (mainManager instanceof GameManager)
			this.gameManager = (GameManager) mainManager;
		else if (mainManager instanceof DebugManager)
			this.debugManager = (DebugManager) mainManager;
		register();
	}
	
	private void register() {
		MoleCraft.getInstance().getCommand(label).setExecutor(this);
	}
	
	@Deprecated
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			Messenger.sendAll(onCommand(null, args));
		else
			Messenger.send((Player) sender, (onCommand((Player) sender, args)));		
		return true;
	}
	
	public abstract String onCommand(Player p, String[] args);

}
