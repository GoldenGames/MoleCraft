package me.mani.molecraft;

import me.mani.molecraft.CountdownManager.CountdownEvent;

import org.bukkit.Sound;

public class CountdownCountEvent extends CountdownEvent {
	
	public CountdownCountEvent(int currentNumber) {
		this.currentNumber = currentNumber;
	}
	
	private int currentNumber;
	
	private String message;
	private Sound sound;	
	private boolean cancel;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean hasMessage() {
		return message != null;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public boolean hasSound() {
		return sound != null;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public int getCurrentNumber() {
		return this.currentNumber;
	}
	
	public void cancelCountdown() {
		cancel = true;
	}
	
	public boolean isCancelled() {
		return cancel;
	}

}
