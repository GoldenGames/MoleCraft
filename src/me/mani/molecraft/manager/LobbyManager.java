package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.List;

import me.mani.molecraft.util.PermanentMessenger;

import org.bukkit.entity.Player;


public class LobbyManager {
	
	private static final String FULL_BLOCK = "█";
	
	private GameManager gameManager;
	private VoteManager voteManager;
	
	private int playerCount = 0;
	private List<Player> parkourSuccesser = new ArrayList<>();
	
	public LobbyManager(GameManager gameManager, VoteManager voteManager) {
		this.gameManager = gameManager;
		this.voteManager = voteManager;
	}
	
	public void addPlayer() {
		playerCount += 1;
		PermanentMessenger.setMessage(generatePlayerStatus());
		if (canStart())
			gameManager.startCountdown();
	}
	
	public void removePlayer() {
		playerCount -= 1;
		PermanentMessenger.setMessage(generatePlayerStatus());
		if (!canStart() && canStop())
			gameManager.stopCountdown();
	}

	private boolean canStart() {
		return playerCount >= 4 && gameManager.getCountdown() == null;
	}
	
	private boolean canStop() {
		return gameManager.getCountdown() != null;
	}
	
	public String generatePlayerStatus() {
		StringBuffer buffer = new StringBuffer("§7Spieler: ");
		for (int i = 0; i < 8; i++) {
			if (playerCount > i && canStart())
				buffer.append("§a");
			else if (playerCount > i)
				buffer.append("§c");
			else
				buffer.append("§7");
			buffer.append(FULL_BLOCK);
		}
		return buffer.toString();
	}
	
	public boolean isParkourSuccesser(Player player) {
		return parkourSuccesser.contains(player);
	}
	
	public Player getParkourSuccesser(int rank) {
		if (parkourSuccesser.size() >= rank + 1)
			return parkourSuccesser.get(rank);
		return null;
	}
	
	public void handleParkourSuccess(Player player) {
		if (parkourSuccesser.size() <= 3)
			parkourSuccesser.add(player);
	}
	
	public VoteManager getVoteManager() {
		return voteManager;
	}	
}
