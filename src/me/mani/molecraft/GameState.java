package me.mani.molecraft;

public enum GameState {
	LOBBY, STARTING, INGAME, FINISH;
	
	private static GameState currentGameState;
	
	public static void setGameState(GameState newGameState) {
		currentGameState = newGameState;
	}
	
	public static GameState getGameState() {
		return currentGameState;
	}

}
