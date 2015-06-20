package me.mani.molecraft;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeCordHandler {
		
	public static void connect(Player p) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("PlayerCommand");
		out.writeUTF(p.getName());
		out.writeUTF("hub");

		p.sendPluginMessage(MoleCraft.getInstance(), "BungeeCord", out.toByteArray());
	}
	
}
