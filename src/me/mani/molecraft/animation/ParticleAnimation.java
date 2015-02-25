package me.mani.molecraft.animation;

import org.bukkit.Effect;

public abstract class ParticleAnimation {
	
	protected Effect effectType;
	
	public ParticleAnimation(Effect effectType) {
		this.effectType = effectType;
	}
	
	public abstract void run( /* Maybe add stuff here */ );

}
