package me.mani.molecraft.animation;

import me.mani.molecraft.util.ParticleEffect.ParticleEffectType;

public abstract class ParticleAnimation {
	
	private ParticleEffectType effectType;
	
	public ParticleAnimation(ParticleEffectType effectType) {
		this.effectType = effectType;
	}
	
	public abstract void run( /* Maybe add stuff here */ );
	
	public ParticleEffectType getType() {
		return effectType;
	}

}
