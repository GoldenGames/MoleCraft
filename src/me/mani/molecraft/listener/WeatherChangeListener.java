package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.manager.MainManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener extends MoleCraftListener {
	
	public WeatherChangeListener(MainManager mainManager) {
		super(mainManager);
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent ev) {
		
		ev.setCancelled(ev.toWeatherState());
		
	}

}
