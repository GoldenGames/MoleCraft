package me.mani.molecraft;

import me.mani.molecraft.manager.DebugManager;
import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MoleCraftCommand implements CommandExecutor {
	
	private MainManager mainManager = MoleCraft.getInstance().gameManager == null ? MoleCraft.getInstance().gameManager : MoleCraft.getInstance().debugManager;
	/** Can only be used if a game manager is active, Otherwise it's null */
	protected GameManager gameManager = mainManager instanceof GameManager ? (GameManager) mainManager : null;
	/** Can only be used if a debug manager is active. Otherwise it's null */
	protected DebugManager debugManager = mainManager instanceof DebugManager ? (DebugManager) mainManager : null;
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
