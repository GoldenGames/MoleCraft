package me.mani.molecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeCordHandler {
	
	public static final String LOBBY = "lobby";
	
	public static void connect(Player p, String serverName) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("Connect");
		out.writeUTF(serverName);

		p.sendPluginMessage(MoleCraft.getInstance(), "BungeeCord", out.toByteArray());
	}
	
	static {
		Bukkit.getMessenger().registerOutgoingPluginChannel(MoleCraft.getInstance(), "BungeeCord");
	}

}
