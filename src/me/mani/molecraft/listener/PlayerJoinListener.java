package me.mani.molecraft.listener;

import me.mani.goldenapi.hologram2.Hologram;
import me.mani.goldenapi.hologram2.Hologram.HologramLineManager;
import me.mani.goldenapi.util.Title;
import me.mani.molecraft.GameState;
import me.mani.molecraft.InventoryManager;
import me.mani.molecraft.InventoryManager.InventoryType;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.StatsManager;
import me.mani.molecraft.game.LobbyManager;
import me.mani.molecraft.game.LocationManager.SpecialLocation;
import me.mani.molecraft.util.PlayerStats;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends MoleCraftListener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		
		if (GameState.getGameState() == GameState.LOBBY) {
			ev.setJoinMessage(Messenger.getPrefix() + "§a[>>>] §e" + ev.getPlayer().getName());
			ev.getPlayer().teleport(SpecialLocation.LOBBY_SPAWN);
			ev.getPlayer().setGameMode(GameMode.ADVENTURE);
			
			InventoryManager.setInventory(InventoryType.LOBBY, ev.getPlayer());
			StatsManager.addPlayerStats(ev.getPlayer());
			LobbyManager.addPlayer();
			
			PlayerStats stats = StatsManager.getPlayerStats(ev.getPlayer());
			
			Hologram hologram = new Hologram(SpecialLocation.STATS_DISPLAY);
			HologramLineManager lm = hologram.getLineManager();
			lm.addLine("§7-=-=---=|=---=-=-");
			lm.addLine("§eDeine Stats:");
			lm.addLine("Kills: §e" + stats.getKills());
			lm.addLine("Deaths: §e" + stats.getDeaths());
			lm.addLine("Wins: §e" + stats.getWins());
			lm.addLine("Games: §e" + stats.getGames());
			lm.addLine("Chests: §e" + stats.getChests());
			lm.addLine("§7-=-=---=|=---=-=-");
			hologram.sendPlayer(ev.getPlayer());
			
			Title title = new Title("§7» §aMoleCraft §7«", "§eSei der wahre §6Mole", 10, 60, 10, false);
			title.send(ev.getPlayer());
		}
		
	}

}
