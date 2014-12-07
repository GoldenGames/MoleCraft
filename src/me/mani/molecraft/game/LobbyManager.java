package me.mani.molecraft.game;

import me.mani.molecraft.CountdownCallback;
import me.mani.molecraft.CountdownCountEvent;
import me.mani.molecraft.CountdownManager;
import me.mani.molecraft.CountdownManager.Countdown;
import me.mani.molecraft.GameState;
import me.mani.molecraft.Manager;
import me.mani.molecraft.util.ParticleEffect;
import me.mani.molecraft.util.ParticleEffect.Offset;
import me.mani.molecraft.util.ParticleEffect.ParticleEffectType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyManager extends Manager {
	
	private static LobbyManager lobby;
	
	private GameManager gameManager;
	private Countdown countdown;
	
	private static int playerCount = 0;
	
	public LobbyManager(GameManager gameManager) {
		lobby = this;
		
		this.gameManager = gameManager;
		
		new AnimationThread().start();
	}
	
	public static void addPlayer() {
		playerCount += 1;
		if (lobby.canStart())
			lobby.startCountdown();
	}
	
	public static void removePlayer() {
		playerCount -= 1;
		if (!lobby.canStart() && lobby.canStop())
			lobby.stopCountdown();
	}

	private boolean canStart() {
		return playerCount >= 2 && countdown == null;
	}
	
	private boolean canStop() {
		return countdown != null;
	}
	
	private void startCountdown() {
		countdown = CountdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				gameManager.start();
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				int i = ev.getCurrentNumber();
				if (i == 30 || i == 20 || i == 10 || i <= 5) {
					ev.setMessage("§7Das Spiel startet in §c" + ev.getCurrentNumber() + " Sekunden.");
					ev.setSound(Sound.NOTE_BASS);
				}
			}
			
		}, 30, 0, 20L);
	}
	
	private void stopCountdown() {
		countdown.forceStop();
		countdown = null;
	}
	
	private class AnimationThread extends Thread {
		
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			try {
				double radius = 0.4;
				double increment = (2*Math.PI)/30;
			
				while (GameState.getGameState() == GameState.LOBBY) {	
					for(int i = 0; i < 30; i++){
						for (Player p : Bukkit.getOnlinePlayers()) {
							Location loc = p.getLocation();
						
							double angle = i*increment;
							double x = loc.getX() + (radius * Math.cos(angle));
							double z = loc.getZ() + (radius * Math.sin(angle));
						
							ParticleEffect.broadcast(ParticleEffectType.FIREWORKS_SPARK, new Location(loc.getWorld(), x, loc.getY() + 1, z), new Offset(0, 0, 0), 0, 1);
						
							
						} 		
						sleep(50);
					}
				}
			}
			catch (InterruptedException e) {
				return;
			}
		}	
	}
	
}
