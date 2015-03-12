package me.mani.molecraft.manager;


public class LobbyManager {
	
	private GameManager gameManager;
	private VoteManager voteManager;
	
	private int playerCount = 0;
	
	public LobbyManager(GameManager gameManager, VoteManager voteManager) {
		this.gameManager = gameManager;
		this.voteManager = voteManager;
	}
	
	public void addPlayer() {
		playerCount += 1;
		if (canStart())
			gameManager.startGameCountdown();
	}
	
	public void removePlayer() {
		playerCount -= 1;
		if (!canStart() && canStop())
			gameManager.stopGameCountdown();
	}

	private boolean canStart() {
		return playerCount >= 2 && gameManager.getGameCountdown() == null;
	}
	
	private boolean canStop() {
		return gameManager.getGameCountdown() != null;
	}
	
	public VoteManager getVoteManager() {
		return voteManager;
	}	
}
