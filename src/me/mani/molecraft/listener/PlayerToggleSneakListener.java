package me.mani.molecraft.listener;

import net.minecraft.server.v1_8_R2.PacketPlayOutCamera;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.MainManager;

public class PlayerToggleSneakListener extends MoleCraftListener {

	public PlayerToggleSneakListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent ev) {
		
		if (MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).isInPlayerView() && ev.isSneaking()) {
			MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).setInPlayerView(false);
			PacketPlayOutCamera cameraPacket = new PacketPlayOutCamera(((CraftEntity) ev.getPlayer()).getHandle());
			((CraftPlayer) ev.getPlayer()).getHandle().playerConnection.sendPacket(cameraPacket);
		}
		
	}

}
