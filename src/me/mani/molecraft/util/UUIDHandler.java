package me.mani.molecraft.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDHandler {
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	public static UUID getUUID(String player) {
		Socket socket = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		try {
			socket = new Socket("89.163.225.13", 1919);
			socket.setSoTimeout(3000);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("4jfnfc9h>>/(>");
			dos.writeUTF("uuid");
			dos.writeUTF(player);
			dos.flush();
			dis = new DataInputStream(socket.getInputStream());
			String u = dis.readUTF();
			if (u.equalsIgnoreCase("err") || u.length() != 36)
				return null;
			return UUID.fromString(u);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			try {
				socket.close();
				dos.close();
				dis.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void getUUID(String player, Consumer<UUID> consumer) {
		executor.execute(() -> consumer.accept(getUUID(player)));	
	}

	public static String getPlayerName(String uuid) {
		Socket socket = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		try {
			socket = new Socket("89.163.225.13", 1919);
			socket.setSoTimeout(3000);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("4jfnfc9h>>/(>");
			dos.writeUTF("playername");
			dos.writeUTF(uuid);
			dos.flush();
			dis = new DataInputStream(socket.getInputStream());
			return dis.readUTF();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			try {
				socket.close();
				dos.close();
				dis.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void getPlayerName(String uuid, Consumer<String> consumer) {
		executor.execute(() -> consumer.accept(getPlayerName(uuid)));
	}

	public static String getPlayerName(UUID uuid) {
		return getPlayerName(uuid.toString());
	}
	
	public static void getPlayerName(UUID uuid, Consumer<String> consumer) {
		getPlayerName(uuid.toString(), consumer);
	}
}