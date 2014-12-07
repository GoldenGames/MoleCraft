package me.mani.molecraft.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.mani.molecraft.CountdownCallback;
import me.mani.molecraft.CountdownCountEvent;
import me.mani.molecraft.CountdownManager;
import me.mani.molecraft.GameState;
import me.mani.molecraft.InventoryManager;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.InventoryManager.InventoryType;
import me.mani.molecraft.Manager;
import me.mani.molecraft.Message;
import me.mani.molecraft.Message.MessageType;
import me.mani.molecraft.game.TeamManager.Team;
import me.mani.molecraft.listener.BlockBreakListener;
import me.mani.molecraft.listener.BlockPlaceListener;
import me.mani.molecraft.listener.EntityExplodeListener;
import me.mani.molecraft.listener.PlayerDamageListener;
import me.mani.molecraft.listener.PlayerDeathListener;
import me.mani.molecraft.listener.PlayerInteractListener;
import me.mani.molecraft.listener.PlayerItemDropPickupListener;
import me.mani.molecraft.listener.PlayerJoinListener;
import me.mani.molecraft.listener.PlayerLoginListener;
import me.mani.molecraft.listener.PlayerMoveListener;
import me.mani.molecraft.listener.PlayerQuitListener;
import me.mani.molecraft.listener.PlayerRespawnListener;
import me.mani.molecraft.listener.PressurePlatePressListener;
import me.mani.molecraft.util.WoolLocation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class GameManager extends Manager {

	public static HashMap<Player, List<WoolLocation>> destroidWool = new HashMap<>();
	private static List<Player> ingamePlayer = new ArrayList<>();
	
	public GameManager() {
		GameState.setGameState(GameState.LOBBY);
		
		// Listener
		
		new BlockBreakListener();
		new BlockPlaceListener();
		new PlayerJoinListener();
		new PlayerQuitListener();	
		new PlayerMoveListener();
		new PlayerItemDropPickupListener();
		new PlayerInteractListener();
		new PlayerDeathListener();
		new PlayerDamageListener();
		new EntityExplodeListener();
		new PressurePlatePressListener();
		new PlayerLoginListener();
		new PlayerRespawnListener();
		
		// Commands
		
		new StatsCommand();
		
		new LobbyManager(this);
	}
	
	public void start() {
		GameState.setGameState(GameState.STARTING);
		
		setupAllPlayers();
		startCountdown();
	}
	
	@SuppressWarnings("deprecation")
	private void setupAllPlayers() {
		int teamId = 1;
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (LocationManager.getSpawn(teamId) != null)
				setup(p, teamId);
			else
				p.kickPlayer("§7[ §cEin Fehler ist aufgetreten! §7]\n§eDu kannst an der Verbesserung dieses Servers beitragen\n§eund den folgenden Code ins Forum stellen.\n \n§cFehler: §e&lERR-001");
			teamId++;
		}
	}
	
	private void setup(Player p, int teamId) {
		p.teleport(LocationManager.getSpawn(teamId));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40*20, 1));
		TeamManager.setTeam(p, Team.getById(teamId));
		InventoryManager.setInventory(InventoryType.INGAME, p);
	}
	
	private void startCountdown() {
		CountdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				startGame();
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				int i = ev.getCurrentNumber();
				if (i == 20 || i == 10 || i <= 3) {
					ev.setMessage("§7Das Spiel startet in §c" + i + " Sekunde" + (i == 1 ? "." : "n."));
					ev.setSound(Sound.ORB_PICKUP);
				}
			}
			
		}, 20, 0, 20L);
	}

	private void startGame() {
		GameState.setGameState(GameState.INGAME);
		
		Message.sendAll(MessageType.CUSTOM.custom(ChatColor.WHITE), "Suche Kisten, töte Gegner und gewinne das Spiel.", 
				"Doch sei immer achtsam jederzeit könnte ein Gegenspieler hinter dir stehen, deine Unsichtbarkeit auslaufen oder dich dein Verstand täuschen.");
	}
	
	public static void finishGame() {
		GameState.setGameState(GameState.FINISH);
		
		CountdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				Bukkit.shutdown();
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				int i = ev.getCurrentNumber();
				if (i == 10 || i <= 3)
					ev.setMessage("§cDer Server startet in §4" + i + " Sekunde" + (i == 1 ? "" : "n") + " neu.");
				if (i == 2)
					for (Player p : Bukkit.getOnlinePlayers())
						connectToHub(p);
			}
			
		}, 15, 0, 20L);
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
	
	private static void connectToHub(Player p) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("Connect");
		out.writeUTF("lobby");

		p.sendPluginMessage(MoleCraft.getInstance(), "BungeeCord", out.toByteArray());
	}
	
}
