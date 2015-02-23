package me.mani.molecraft.game;

import me.mani.molecraft.CountdownManager;
import me.mani.molecraft.CountdownManager.Countdown;
import me.mani.molecraft.Manager;

import org.bukkit.Sound;

public class LobbyManager extends Manager {
	
	private GameManager gameManager;
	private Countdown countdown;
	
	private static int playerCount = 0;
	
	public LobbyManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public void addPlayer() {
		playerCount += 1;
		if (canStart())
			startCountdown();
	}
	
	public void removePlayer() {
		playerCount -= 1;
		if (!canStart() && canStop())
			stopCountdown();
	}

	private boolean canStart() {
		return playerCount >= 2 && countdown == null;
	}
	
	private boolean canStop() {
		return countdown != null;
	}
	
	private void startCountdown() {
		countdown = CountdownManager.createCountdown((ev) -> {
			
			int i = ev.getCurrentNumber();
			if (i == 30 || i == 20 || i == 10 || i <= 5) {
				ev.setMessage("§7Das Spiel startet in §c" + ev.getCurrentNumber() + " Sekunden.");
				ev.setSound(Sound.NOTE_BASS);
			}
			
		}, (ev) -> gameManager.start(), 30, 0, 20L);
	}
	
	private void stopCountdown() {
		countdown.stop(true);
		countdown = null;
	}
}
