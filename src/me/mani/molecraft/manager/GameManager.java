package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.BungeeCordHandler;
import me.mani.molecraft.CountdownManager;
import me.mani.molecraft.CountdownManager.Countdown;
import me.mani.molecraft.GameState;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.listener.StatsEvent;
import me.mani.molecraft.listener.StatsEvent.StatsEventType;
import me.mani.molecraft.listener.StatsListener;
import me.mani.molecraft.manager.InventoryManager.InventoryType;
import me.mani.molecraft.manager.SetupManager.SetupException;
import me.mani.molecraft.manager.TeamManager.Team;
import me.mani.molecraft.util.PlayerStats;
import me.mani.molecraft.util.WoolLocation;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameManager implements MainManager {

	private MoleCraft moleCraft;
	private SetupManager setupManager;
	public LobbyManager lobbyManager;
	public ArenaMapManager arenaMapManager;
	public LocationManager locationManager;
	public ChestManager chestManager;
	public TeamManager teamManager;
	public InventoryManager inventoryManager;
	public StatsManager statsManager;
	public SidebarManager sidebarManager;
	
	private Countdown votingCountdown;
	public static HashMap<Player, List<WoolLocation>> destroidWool = new HashMap<>();
	
	public GameManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
	}
	
	public void startBootstrap() {
		setupManager = new SetupManager(moleCraft);
		if (!setupManager.setup()) {
			System.out.println("Failed to start molecraft.");
			// Game needs to be checked here
		}
				
		arenaMapManager = setupManager.getArenaMapManager();
		locationManager = setupManager.getLocationManager();
		teamManager = new TeamManager();
		inventoryManager = new InventoryManager(this);
		inventoryManager.createVotingInventory(arenaMapManager.getArenaMapPack());
		lobbyManager = new LobbyManager(this, new VoteManager(7));
		statsManager = new StatsManager(setupManager.getSQL());
		PlayerStats.addDatabaseManager(setupManager.getSQL());
		statsManager.setupStatsBoard();
		sidebarManager = new SidebarManager();
		
		GameState.setGameState(GameState.LOBBY);
	}
	
	public void stopBootstrap() {
		// TODO: Add something here
	}
	
	public void startVotingCountdown() {
		votingCountdown = CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 40 || i == 25 || i == 10 || i <= 5) {
				ev.setMessage("§7Das Voting ist beendet" + (i != 0 ? " in §e" + i + " Sekunde" + (i == 1 ? "." : "n.") : "."));
			}
			
		}, (ev) -> startGameCountdown(), 40, 0, 20L);
	}
	
	public Countdown getVotingCountdown() {
		return votingCountdown;
	}
	
	public void stopVotingCountdown() {
		votingCountdown.stop(true);
		votingCountdown = null;
	}
	
	public void startGameCountdown() {
		ArenaMap arenaMap;
		try {
			arenaMap = arenaMapManager.setupArenaMap(Bukkit.getWorld("map"), lobbyManager.getVoteManager().getWinningChoiseId());
			chestManager = arenaMapManager.addChests(arenaMap, 30);
			chestManager.fillAll();
		} catch (SetupException e) {
			e.printStackTrace();
			for (Player player : Bukkit.getOnlinePlayers())
				player.kickPlayer("The map couldn't be loaded.\nAn admin is informed.");
			return;
		} // XXX: A bit hardcoded here
		
		Messenger.sendAll("Wir spielen §b" + arenaMap.getDisplayName() + " §evon §b" + arenaMap.getBuilderName() + "§e.");
		
		CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 10 || i <= 5) {
				ev.setMessage("§7Das Spiel startet" + (i != 0 ? " in §e" + i + " Sekunde" + (i == 1 ? "." : "n.") : "."));
				ev.setSound(Sound.NOTE_BASS);
			}
			
		}, (ev) -> startWarmUp(), 10, 0, 20L);
	}
	
	private void startWarmUp() {
		GameState.setGameState(GameState.WARM_UP);
		
		moleCraft.cancelAutoRestart();
		sidebarManager.setupMap(ArenaMap.getCurrentMap());
		sidebarManager.setupIngameSidebar(ArenaMap.getCurrentMap());
		Bukkit.getScheduler().runTaskTimer(moleCraft, () -> sidebarManager.updateIngameSidebar(), 10L, 200L);
		preparePlayers();

		CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 20 || i == 10 || i <= 3) {
				ev.setMessage("§7Das Spiel startet" + (i != 0 ? " in §c" + i + " Sekunde" + (i == 1 ? "." : "n.") : "."));
				ev.setSound(Sound.ORB_PICKUP);
			}
			
		}, (ev) -> startGame(), 20, 0, 20L);
	}
	
	
	
	private void startGame() {
		GameState.setGameState(GameState.INGAME);
		
		Messenger.sendAll(
			"§fSuche Kisten, töte Gegner und gewinne das Spiel." + "\n" +
			"§fDoch sei immer achtsam jederzeit könnte ein Gegenspieler hinter dir stehen, deine Unsichtbarkeit auslaufen oder dich dein Verstand täuschen."
		);
		
		new RandomEventManager(this).startRandomEventTask();
	}
	
	public void finishGame() {
		GameState.setGameState(GameState.SHUTDOWN);
		
		statsManager.sendAll();
		
		CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 10 || i <= 3)
				ev.setMessage("§cDer Server startet in §4" + i + " Sekunde" + (i == 1 ? "" : "n") + " neu.");
			if (i == 2)
				for (Player player : Bukkit.getOnlinePlayers())
					BungeeCordHandler.connect(player, BungeeCordHandler.LOBBY);
			
		}, (ev) -> Bukkit.shutdown(), 15, 0, 20L);
	}
	
	private void preparePlayers() {
		Player[] allPlayers = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
		
		for (int team = 0; team < 7; team++) {
			if (allPlayers.length < team + 1)
				break;
			Player player = allPlayers[team];
			player.teleport(locationManager.getSpawnLocation(team));
			if (!lobbyManager.isParkourSuccesser(player))
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 0));
			player.setGameMode(GameMode.SURVIVAL);
			player.setScoreboard(sidebarManager.getScoreboard(player));
			teamManager.setTeam(player, Team.getById(team));
			inventoryManager.setInventory(player, InventoryType.INGAME);
			StatsListener.onStatsChange(this, new StatsEvent(player, StatsEventType.GAME, 1));
		}
		
		for (int rank = 0; rank < 2; rank++)
			if (lobbyManager.getParkourSuccesser(rank) != null) {
				lobbyManager.getParkourSuccesser(rank).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (4 - rank) * 600 + 1200, 0));
				System.out.println((4 - rank) * 20 * 60 + 8000);
				Messenger.send(lobbyManager.getParkourSuccesser(rank), "Du erhälst " + (4 - rank) * 0.5 + " min Bonusunsichtbarkeit, da du das Jump 'N' Run als " + (rank + 1) + ". geschafft hast.");
			}
	}
	
	public List<Player> getPlayerLeft() {
		List<Player> playerLeft = new ArrayList<>();
		MoleCraftPlayer.getMoleCraftPlayers().forEach((moleCraftPlayer) -> {
			if (moleCraftPlayer.isIngame())
				playerLeft.add(moleCraftPlayer.getPlayer());
		});
		return playerLeft;
	}
	
	@SuppressWarnings("deprecation")
	public void replaceWool(Player p) {
		for (WoolLocation woolLoc : destroidWool.get(p)) {
			woolLoc.getLocation().getBlock().setType(Material.WOOL);
			woolLoc.getLocation().getBlock().setData(woolLoc.getColor());
		}
	}

	@Override
	public MoleCraft getMoleCraft() {
		return moleCraft;
	}	
}
