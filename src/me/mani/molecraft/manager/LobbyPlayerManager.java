package me.mani.molecraft.manager;

// XXX: Find a better name for this
public class LobbyPlayerManager {
	
	private GameManager gameManager;
	
	private static int playerCount = 0;
	
	public LobbyPlayerManager(GameManager gameManager) {
		this.gameManager = gameManager;
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
		return playerCount >= 1 && gameManager.getGameCountdown() == null;
	}
	
	private boolean canStop() {
		return gameManager.getGameCountdown() != null;
	}

}
