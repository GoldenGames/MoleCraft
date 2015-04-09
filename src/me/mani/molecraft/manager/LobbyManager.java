package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;


public class LobbyManager {
	
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
		if (canStart())
			gameManager.startVotingCountdown();
	}
	
	public void removePlayer() {
		playerCount -= 1;
		if (!canStart() && canStop())
			gameManager.stopVotingCountdown();
	}

	private boolean canStart() {
		return playerCount >= 1 && gameManager.getVotingCountdown() == null;
	}
	
	private boolean canStop() {
		return gameManager.getVotingCountdown() != null;
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
