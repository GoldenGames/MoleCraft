package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.BungeeCordHandler;
import me.mani.molecraft.Constants;
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
import me.mani.molecraft.util.PermanentMessenger;
import me.mani.molecraft.util.PlayerStats;
import me.mani.molecraft.util.WoolLocation;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.NumberConversions;

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
	
	private BukkitTask waitingNotifierTask;
	private Countdown votingCountdown;
	public static HashMap<Player, List<WoolLocation>> destroidWool = new HashMap<>();
	private static int gameId;
	
	public GameManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
	}
	
	public void startBootstrap() {
		setupManager = new SetupManager(moleCraft);
		if (!setupManager.setup()) {
			System.out.println("Failed to start molecraft.");
			// TODO: Game needs to be checked here
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
		
		gameId = NumberConversions.toInt(setupManager.getSQL().get(Constants.MAIN_TABLE, "key", Constants.TOTAL_GAME_COUNT, "value"));
		setupManager.getSQL().update(Constants.MAIN_TABLE, "key", Constants.TOTAL_GAME_COUNT, "value", gameId + 1);
		
		GameState.setGameState(GameState.LOBBY);
		startWaitingNotifier();
		
		PermanentMessenger.start((message) -> Messenger.sendBarAll(message, true), 20L);
	}
	
	private void startWaitingNotifier() {
		waitingNotifierTask = Bukkit.getScheduler().runTaskTimer(moleCraft, () -> Messenger.sendAll("Mindestens" + Messenger.getSuffix() + " 4 Spieler §rbenötigt."), 0L, 20L * 30L);
	}
	
	public void stopBootstrap() {
		// TODO: Add something here
	}
	
	public void startCountdown() {
		waitingNotifierTask.cancel();
		
		votingCountdown = CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 60 || i == 40 || i == 25 || i == 10 || i <= 5) 
				ev.setMessage("Das Spiel beginnt" + (i != 0 ? " in " + Messenger.getSuffix() + i + " Sekunde" + (i == 1 ? "§r." : "n§r.") : "§r."));
			if (i == 10)
				startPreGame();
			for (Player player : Bukkit.getOnlinePlayers())
				player.setLevel(i);
			
		}, (ev) -> startWarmUp(), 60, 0, 20L);
		
		CountdownManager.createCountdown((ev) -> {
			
			for (Player player : Bukkit.getOnlinePlayers())
				player.setExp((float) ev.getCurrentNumber() / 1200f);
			
		}, (ev) -> {}, 1200, 0, 1L);
	}
	
	public Countdown getCountdown() {
		return votingCountdown;
	}
	
	public void skipCountdown() {
		if (votingCountdown != null) {
			votingCountdown.stop(true);
			votingCountdown = null;
		}
		
		waitingNotifierTask.cancel();
		votingCountdown = CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 10 || i <= 5) 
				ev.setMessage("Das Spiel beginnt" + (i != 0 ? " in " + Messenger.getSuffix() + i + " Sekunde" + (i == 1 ? "§r." : "n§r.") : "§r."));
			for (Player player : Bukkit.getOnlinePlayers())
				player.setLevel(i);
			
		}, (ev) -> startWarmUp(), 10, 0, 20L);
		
		CountdownManager.createCountdown((ev) -> {
			
			for (Player player : Bukkit.getOnlinePlayers())
				player.setExp((float) ev.getCurrentNumber() / 200f);
			
		}, (ev) -> {}, 200, 0, 1L);
		
		startPreGame();
	}
	
	public void stopCountdown() {
		votingCountdown.stop(true);
		votingCountdown = null;
		
		Messenger.sendAll("Der Countdown wurde wegen" + Messenger.getSuffix() + " zu wenig Spielern §rabgebrochen.");
		startWaitingNotifier();
	}
	
	public void startPreGame() {
		ArenaMap arenaMap;
		try {
			arenaMap = arenaMapManager.setupArenaMap(Bukkit.getWorld("map"), lobbyManager.getVoteManager().getWinningChoiseId());
			chestManager = arenaMapManager.addChests(arenaMap, 30, 4);
			chestManager.fillAll();
		} catch (SetupException e) {
			e.printStackTrace();
			for (Player player : Bukkit.getOnlinePlayers())
				player.kickPlayer("The map couldn't be loaded.\nAn admin is informed.");
			return;
		} // XXX: A bit hardcoded here
		
		Messenger.sendAll("Wir spielen " + Messenger.getSuffix() + arenaMap.getDisplayName() + " §rvon " + Messenger.getSuffix() + arenaMap.getBuilderName() + "§r.");
	}
	
	private void startWarmUp() {
		GameState.setGameState(GameState.WARM_UP);
		
		PermanentMessenger.stop();
		moleCraft.cancelAutoRestart();
		preparePlayers();
		sidebarManager.setupMap(ArenaMap.getCurrentMap());
		sidebarManager.setupIngameSidebar(ArenaMap.getCurrentMap());
		Bukkit.getScheduler().runTaskTimer(moleCraft, () -> sidebarManager.updateIngameSidebar(), 10L, 40L);	

		CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 20 || i == 10 || i <= 3) {
				ev.setMessage("Das Spiel startet" + (i != 0 ? " in §c" + i + " Sekunde" + (i == 1 ? "§r." : "n§r.") : "§r."));
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
		
		if (MoleCraftPlayer.getMoleCraftPlayers().size() <= 1) {
			Messenger.sendAll("§cDas Spiel hat zu wenig Spieler und wird abgebrochen.");
			finishGame();
			return;
		}
		
		new RandomEventManager(this).startRandomEventTask();
		
		Bukkit.getScheduler().runTaskLater(moleCraft, () -> {
			
			MoleCraftPlayer.getMoleCraftPlayers().forEach((moleCraftPlayer) -> {
				if (!moleCraftPlayer.isIngame()) {
					Messenger.send(moleCraftPlayer.getPlayer(), "Verlasse bitte in" + Messenger.getSuffix() + " 10 Sekunden §rdie Spawnkammer.");
					Bukkit.getScheduler().runTaskLater(moleCraft, () -> {
						if (!moleCraftPlayer.isIngame())
							moleCraftPlayer.getPlayer().damage(20D);
					}, 20L * 10L);
				}
			});
			
		}, 20L * 30L);
	}
	
	public void finishGame() {
		GameState.setGameState(GameState.SHUTDOWN);
		
		statsManager.sendAll();
		
		CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 10 || i <= 3)
				ev.setMessage("§cDer Server startet in " + i + " Sekunde" + (i == 1 ? "" : "n") + " neu.");
			
		}, (ev) -> {
			for (Player player : Bukkit.getOnlinePlayers())
				BungeeCordHandler.connect(player);
			Bukkit.getScheduler().runTaskLater(moleCraft, () -> Bukkit.shutdown(), 20L * 5);
		}, 15, 0, 20L);
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
			teamManager.setTeam(player, Team.getById(team));
			inventoryManager.setInventory(player, InventoryType.INGAME);
			StatsListener.onStatsChange(this, new StatsEvent(player, StatsEventType.GAME, 1));
		}
		
		for (int rank = 0; rank < 2; rank++)
			if (lobbyManager.getParkourSuccesser(rank) != null) {
				lobbyManager.getParkourSuccesser(rank).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (4 - rank) * 600 + 1200, 0));
				System.out.println((4 - rank) * 20 * 60 + 8000);
				Messenger.send(lobbyManager.getParkourSuccesser(rank), "Du erhälst " + Messenger.getSuffix() + (4 - rank) * 0.5 + " min Bonusunsichtbarkeit§r, da du das Jump 'N' Run als " + (rank + 1) + ". geschafft hast.");
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

	public static int getGameId() {
		return gameId;
	}
	
	@Override
	public MoleCraft getMoleCraft() {
		return moleCraft;
	}	
}
