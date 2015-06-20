package me.mani.molecraft.listener;

import me.mani.goldenapi.hologram2.Hologram;
import me.mani.goldenapi.hologram2.Hologram.HologramLineManager;
import me.mani.goldenapi.util.Title;
import me.mani.molecraft.GameState;
import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.InventoryManager.InventoryType;
import me.mani.molecraft.manager.MainManager;
import me.mani.molecraft.manager.SpectatorManager;
import me.mani.molecraft.util.NametagUtil;
import me.mani.molecraft.util.PlayerStats;
import me.mani.molecraft.util.RankHandler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener extends MoleCraftListener {
	
	public PlayerJoinListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		
		new MoleCraftPlayer(ev.getPlayer());
		if (GameState.getGameState() != GameState.LOBBY)
			SpectatorManager.setSpectator(ev.getPlayer());
		
//		Bukkit.getScheduler().runTaskLater(gameManager.getMoleCraft(), () -> ev.getPlayer().setResourcePack("http://craplezz.de/resource/moleCraft.zip"), 1L);
//		
//		PacketListener.listen(ev.getPlayer(), (packet) -> {
//			try {
//				Field field = PacketPlayInResourcePackStatus.class.getDeclaredField("b");
//				field.setAccessible(true);
//				if ((EnumResourcePackStatus) (field.get(packet)) == EnumResourcePackStatus.DECLINED || (EnumResourcePackStatus) (field.get(packet)) == EnumResourcePackStatus.FAILED_DOWNLOAD)
//					Bukkit.getScheduler().runTask(gameManager.getMoleCraft(), () -> ev.getPlayer().kickPlayer("§cDu musst das Resourcepack akzeptieren!"));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		});
		
		if (GameState.getGameState() == GameState.LOBBY) {
			ev.setJoinMessage("§a[>>>] §e" + ev.getPlayer().getDisplayName());
			ev.getPlayer().teleport(gameManager.locationManager.LOBBY_SPAWN);
			ev.getPlayer().setGameMode(GameMode.ADVENTURE);
			NametagUtil.changeNametag(ev.getPlayer(), RankHandler.getTabColor(ev.getPlayer()));
			Bukkit.getScheduler().runTaskLater(gameManager.getMoleCraft(), () -> 
				ev.getPlayer().setPlayerListName(RankHandler.getTabColor(ev.getPlayer()) + ev.getPlayer().getDisplayName()),
			1L);
			
//			Bukkit.getScheduler().runTaskLater(gameManager.getMoleCraft(), () -> {
//				ev.getPlayer().setDisplayName(RankHandler.getTabColor(ev.getPlayer()) + ev.getPlayer().getName());
//				CustomTablist.sendCustomTablist(ev.getPlayer());
//				for (Player onlinePlayer : Bukkit.getOnlinePlayers())
//					if (!onlinePlayer.equals(ev.getPlayer()))
//						CustomTablist.updateCustomTablist(onlinePlayer);
//			}, 1L);
			
			gameManager.inventoryManager.setInventory(ev.getPlayer(), InventoryType.LOBBY);		
			gameManager.lobbyManager.addPlayer();
			
			PlayerStats.getPlayerStats(ev.getPlayer(), (stats) -> {
				Hologram hologram = new Hologram(gameManager.locationManager.STATS_DISPLAY);
				HologramLineManager lm = hologram.getLineManager();
				lm.addLine("§7-=-=---=|=---=-=-");
				lm.addLine("§eDeine Stats:");
				lm.addLine("Rang: §e" + stats.getRanking());
				lm.addLine("Punkte: §e" + stats.getPoints());
				lm.addLine("Kills: §e" + stats.getKills());
				lm.addLine("Tode: §e" + stats.getDeaths());
				lm.addLine("K/D: §e" + (double) Math.round((double) stats.getKills() / ((double) stats.getDeaths() < 1 ? 1 : stats.getDeaths()) * 100) / 100);
				lm.addLine("Siege: §e" + stats.getWins());
				lm.addLine("Spiele gespielt: §e" + stats.getGames());
				lm.addLine("Siegwahrscheinlichkeit: §e" + Math.round((double) stats.getWins() / ((double) stats.getGames() < 1 ? 1 : stats.getGames()) * 100) + "%");
				lm.addLine("Kisten geöffnet: §e" + stats.getChests());
				lm.addLine("§7-=-=---=|=---=-=-");
				hologram.sendPlayer(ev.getPlayer());
				
				Title title = new Title("§7» §aMoleCraft §7«", "§7Dein Rang: §e" + stats.getRanking(), 10, 60, 10, false);
				title.send(ev.getPlayer());
			});
		}
		
	}

}
