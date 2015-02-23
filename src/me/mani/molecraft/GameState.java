package me.mani.molecraft;

public enum GameState {
	SETUP, LOBBY, WARM_UP, INGAME, SHUTDOWN;
	
	private static GameState currentGameState;
	
	public static void setGameState(GameState newGameState) {
		currentGameState = newGameState;
	}
	
	public static GameState getGameState() {
		return currentGameState;
	}

}
