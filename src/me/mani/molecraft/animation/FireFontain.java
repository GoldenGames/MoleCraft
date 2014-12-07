package me.mani.molecraft.animation;

import me.mani.goldenapi.GoldenAPI;
import me.mani.molecraft.util.ParticleEffect;
import me.mani.molecraft.util.ParticleEffect.Offset;
import me.mani.molecraft.util.ParticleEffect.ParticleEffectType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public class FireFontain extends TimedParticleAnimation {

	private Location loc;
	
	private BukkitTask task;
	
	public FireFontain(Location loc) {
		super(ParticleEffectType.LAVA, 6, 3);
		this.loc = loc;
	}
	
	@Override
	public void run() {	
		task = Bukkit.getScheduler().runTaskTimer(GoldenAPI.getInstance(), new Runnable() {
			
			int i = 0;
			
			@Override
			public void run() {
				if (i == getTimes())
					task.cancel();
				
				ParticleEffect.broadcast(getType(), loc, new Offset(0.5f, 0.5f, 0.5f), 0, 25);
				loc.add(0, 0.5, 0);
				
				i++;
			}
			
		}, 0L, getDelay());
	}

}
