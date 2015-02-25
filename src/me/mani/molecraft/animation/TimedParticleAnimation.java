package me.mani.molecraft.animation;

import org.bukkit.Effect;

public class TimedParticleAnimation extends ParticleAnimation {

	private int times;
	private long delay;
	
	public TimedParticleAnimation(Effect effectType, int times, long delay) {
		super(effectType);
		this.times = times;
		this.delay = delay;
	}
	
	public int getTimes() {
		return times;
	}
	
	public long getDelay() {
		return delay;
	}

	@Override
	public void run() {}
	
}
