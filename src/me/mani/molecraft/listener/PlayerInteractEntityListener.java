package me.mani.molecraft.listener;

import me.mani.molecraft.MoleCraftListener;
import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.manager.MainManager;
import net.minecraft.server.v1_8_R2.PacketPlayOutCamera;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener extends MoleCraftListener {

	public PlayerInteractEntityListener(MainManager mainManager) {
		super(mainManager);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent ev) {
		
		if (!(ev.getRightClicked() instanceof Player))
			ev.setCancelled(true);
		else if (MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).isSpectator() && MoleCraftPlayer.getMoleCraftPlayer((Player) ev.getRightClicked()).isIngame()) {
			MoleCraftPlayer.getMoleCraftPlayer(ev.getPlayer()).setInPlayerView(true);
			PacketPlayOutCamera cameraPacket = new PacketPlayOutCamera(((CraftEntity) ev.getRightClicked()).getHandle());
			((CraftPlayer) ev.getPlayer()).getHandle().playerConnection.sendPacket(cameraPacket);
		}
		
	}

}
