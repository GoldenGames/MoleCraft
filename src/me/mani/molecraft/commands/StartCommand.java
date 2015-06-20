package me.mani.molecraft.commands;

import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftCommand;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StartCommand extends MoleCraftCommand {

	public StartCommand(MainManager mainManager) {
		super("start", mainManager);
	}

	@Override
	public String onCommand(Player p, String[] args) {
		
		if (GameState.getGameState() == GameState.LOBBY) {
			if (!p.hasPermission("myrealms.team"))
				return "Du hast keine Rechte für diesen Befehl";
			else if (Bukkit.getOnlinePlayers().size() <= 1)
				return "Es sind zu wenig Spieler auf dem Server";
			gameManager.skipCountdown();
			Messenger.sendAll(Messenger.getSuffix() + p.getDisplayName() + " §rhat das Spiel gestartet.");
			return "Du hast das Spiel erfolgreich gestartet.";
		}
		
		return "Du kannst diesen Befehl nicht ausführen.";
	}
}
