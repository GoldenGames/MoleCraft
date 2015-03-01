package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.mani.molecraft.BungeeCordHandler;
import me.mani.molecraft.CountdownManager;
import me.mani.molecraft.CountdownManager.Countdown;
import me.mani.molecraft.GameState;
import me.mani.molecraft.InventoryManager;
import me.mani.molecraft.InventoryManager.InventoryType;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.manager.TeamManager.Team;
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
	public LobbyPlayerManager lobbyPlayerManager;
	public LocationManager locationManager;
	public TeamManager teamManager;
//	public StatsManager statsManager;
	public InventoryManager inventoryManager;
	public StatsManager statsManager;
	
	private Countdown gameCountdown;
	public static HashMap<Player, List<WoolLocation>> destroidWool = new HashMap<>();
	private static List<Player> ingamePlayer = new ArrayList<>();
	
	public GameManager(MoleCraft moleCraft) {
		this.moleCraft = moleCraft;
	}
	
	public void startBootstrap() {
		setupManager = new SetupManager(moleCraft);
		if (!setupManager.setup()) {
			System.out.println("Failed to start molecraft.");
			// Game needs to be checked here
		}
				
		locationManager = setupManager.getLocationManager();
		teamManager = new TeamManager();
		inventoryManager = new InventoryManager(this);
		lobbyPlayerManager = new LobbyPlayerManager(this);
		
		GameState.setGameState(GameState.LOBBY);
	}
	
	public void stopBootstrap() {
		
	}
	
	public void startGameCountdown() {
		gameCountdown = CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 30 || i == 20 || i == 10 || i <= 5) {
				ev.setMessage("§7Das Spiel startet " + (i != 0 ? "in §e" + i + " Sekunde" + (i == 1 ? "." : "n.") : "."));
				ev.setSound(Sound.NOTE_BASS);
			}
			
		}, (ev) -> startWarmUp(), 30, 0, 20L);
	}
	
	public Countdown getGameCountdown() {
		return gameCountdown;
	}
	
	public void stopGameCountdown() {
		gameCountdown.stop(true);
		gameCountdown = null;
	}
	
	private void startWarmUp() {
		GameState.setGameState(GameState.WARM_UP);
		
		preparePlayers();

		CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 20 || i == 10 || i <= 3) {
				ev.setMessage("§7Das Spiel startet " + (i != 0 ? "in §c" + i + " Sekunde" + (i == 1 ? "." : "n.") : "."));
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
	}
	
	public static void finishGame() {
		GameState.setGameState(GameState.SHUTDOWN);
		
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
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40*20, 0));
			player.setGameMode(GameMode.SURVIVAL);
			teamManager.setTeam(player, Team.getById(team));
			inventoryManager.setInventory(player, InventoryType.INGAME);
		}
	}
	
	public static void addIngamePlayer(Player p) {
		ingamePlayer.add(p);
	}
	
	public static void removeIngamePlayer(Player p) {
		ingamePlayer.remove(p);
	}
	
	public static boolean isIngame(Player p) {
		return ingamePlayer.contains(p);
	}
	
	public static List<Player> getPlayerLeft() {
		return ingamePlayer;
	}
	
	@SuppressWarnings("deprecation")
	public static void replaceWool(Player p) {
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
