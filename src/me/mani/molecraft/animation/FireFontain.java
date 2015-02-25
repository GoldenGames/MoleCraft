package me.mani.molecraft.animation;

import me.mani.goldenapi.GoldenAPI;
import me.mani.molecraft.Effects;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public class FireFontain extends TimedParticleAnimation {

	private Location loc;
	
	private BukkitTask task;
	
	public FireFontain(Location loc) {
		super(Effect.LAVA_POP, 6, 3);
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
				
				Effects.showAll(effectType, loc);
//				broadcast(getType(), loc, new Offset(0.5f, 0.5f, 0.5f), 0, 25);
				loc.add(0, 0.5, 0);
				
				i++;
			}
			
		}, 0L, getDelay());
	}

}
